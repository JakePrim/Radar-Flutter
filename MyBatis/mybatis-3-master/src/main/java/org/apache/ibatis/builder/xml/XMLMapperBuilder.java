/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.builder.xml;

import org.apache.ibatis.builder.*;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.InputStream;
import java.io.Reader;
import java.util.*;

/**
 * Mapper XML 配置构建器，主要负责解析 Mapper 映射配置文件
 *
 * @author Clinton Begin
 */
public class XMLMapperBuilder extends BaseBuilder {

    /**
     * 基于 Java XPath 解析器
     */
    private final XPathParser parser;
    /**
     * Mapper 构造器助手
     */
    private final MapperBuilderAssistant builderAssistant;
    /**
     * 可被其他语句引用的可重用语句块的集合
     *
     * 例如：<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
     */
    private final Map<String, XNode> sqlFragments;
    /**
     * 资源引用的地址
     */
    private final String resource;

    @Deprecated
    public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
        this(reader, configuration, resource, sqlFragments);
        this.builderAssistant.setCurrentNamespace(namespace);
    }

    @Deprecated
    public XMLMapperBuilder(Reader reader, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        this(new XPathParser(reader, true, configuration.getVariables(), new XMLMapperEntityResolver()),
                configuration, resource, sqlFragments);
    }

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments, String namespace) {
        this(inputStream, configuration, resource, sqlFragments);
        this.builderAssistant.setCurrentNamespace(namespace);
    }

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        this(new XPathParser(inputStream, true, configuration.getVariables(), new XMLMapperEntityResolver()),
                configuration, resource, sqlFragments);
    }

    private XMLMapperBuilder(XPathParser parser, Configuration configuration, String resource, Map<String, XNode> sqlFragments) {
        super(configuration);
        // 创建 MapperBuilderAssistant 对象
        this.builderAssistant = new MapperBuilderAssistant(configuration, resource);
        this.parser = parser;
        this.sqlFragments = sqlFragments;
        this.resource = resource;
    }

    public void parse() {
        // 判断当前 Mapper 是否已经加载过
        if (!configuration.isResourceLoaded(resource)) {
            // 解析 `<mapper />` 节点
            configurationElement(parser.evalNode("/mapper"));
            // 标记该 Mapper 已经加载过
            configuration.addLoadedResource(resource);
            // 绑定 Mapper
            bindMapperForNamespace();
        }

        // 解析待定的 <resultMap /> 节点
        parsePendingResultMaps();
        // 解析待定的 <cache-ref /> 节点
        parsePendingCacheRefs();
        // 解析待定的 SQL 语句的节点
        parsePendingStatements();
    }

    public XNode getSqlFragment(String refid) {
        return sqlFragments.get(refid);
    }

    // 解析 `<mapper />` 节点
    private void configurationElement(XNode context) {
        try {
            // 获得 namespace 属性
            String namespace = context.getStringAttribute("namespace");
            if (namespace == null || namespace.equals("")) {
                throw new BuilderException("Mapper's namespace cannot be empty");
            }
            // 设置 namespace 属性
            builderAssistant.setCurrentNamespace(namespace);
            // 解析 <cache-ref /> 节点 当前的mapper中引入其他缓存时会用到这个标签
            cacheRefElement(context.evalNode("cache-ref"));
            // 解析 <cache /> 节点
            cacheElement(context.evalNode("cache"));
            // 已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。
            parameterMapElement(context.evalNodes("/mapper/parameterMap"));
            // 解析 <resultMap /> 节点们
            resultMapElements(context.evalNodes("/mapper/resultMap"));
            // 解析 <sql /> 节点们
            sqlElement(context.evalNodes("/mapper/sql"));
            // 解析 <select /> <insert /> <update /> <delete /> 节点们
            // 这里会将生成的Cache包装到对应的MappedStatement
            buildStatementFromContext(context.evalNodes("select|insert|update|delete"));
        } catch (Exception e) {
            throw new BuilderException("Error parsing Mapper XML. The XML location is '" + resource + "'. Cause: " + e, e);
        }
    }

    // 解析 <select /> <insert /> <update /> <delete /> 节点们
    private void buildStatementFromContext(List<XNode> list) {
        if (configuration.getDatabaseId() != null) {
            buildStatementFromContext(list, configuration.getDatabaseId());
        }
        buildStatementFromContext(list, null);
        // 上面两块代码，可以简写成 buildStatementFromContext(list, configuration.getDatabaseId());
    }

    private void buildStatementFromContext(List<XNode> list, String requiredDatabaseId) {
        //遍历 <select /> <insert /> <update /> <delete /> 节点们
        for (XNode context : list) {
            // 创建 XMLStatementBuilder 对象，执行解析
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, context, requiredDatabaseId);
            try {

                // 每一条执行语句转换成一个MappedStatement
                statementParser.parseStatementNode();
            } catch (IncompleteElementException e) {
                // 解析失败，添加到 configuration 中
                configuration.addIncompleteStatement(statementParser);
            }
        }
    }

    private void parsePendingResultMaps() {
        // 获得 ResultMapResolver 集合，并遍历进行处理
        Collection<ResultMapResolver> incompleteResultMaps = configuration.getIncompleteResultMaps();
        synchronized (incompleteResultMaps) {
            Iterator<ResultMapResolver> iter = incompleteResultMaps.iterator();
            while (iter.hasNext()) {
                try {
                    // 执行解析
                    iter.next().resolve();
                    // 移除
                    iter.remove();
                } catch (IncompleteElementException e) {
                    // ResultMap is still missing a resource...
                    // 解析失败，不抛出异常
                }
            }
        }
    }

    private void parsePendingCacheRefs() {
        // 获得 CacheRefResolver 集合，并遍历进行处理
        Collection<CacheRefResolver> incompleteCacheRefs = configuration.getIncompleteCacheRefs();
        synchronized (incompleteCacheRefs) {
            Iterator<CacheRefResolver> iter = incompleteCacheRefs.iterator();
            while (iter.hasNext()) {
                try {
                    // 执行解析
                    iter.next().resolveCacheRef();
                    // 移除
                    iter.remove();
                } catch (IncompleteElementException e) {
                    // Cache ref is still missing a resource...
                }
            }
        }
    }

    private void parsePendingStatements() {
        // 获得 XMLStatementBuilder 集合，并遍历进行处理
        Collection<XMLStatementBuilder> incompleteStatements = configuration.getIncompleteStatements();
        synchronized (incompleteStatements) {
            Iterator<XMLStatementBuilder> iter = incompleteStatements.iterator();
            while (iter.hasNext()) {
                try {
                    // 执行解析
                    iter.next().parseStatementNode();
                    // 移除
                    iter.remove();
                } catch (IncompleteElementException e) {
                    // Statement is still missing a resource...
                }
            }
        }
    }

    // 解析 <cache-ref /> 标签
    private void cacheRefElement(XNode context) {
        if (context != null) {
            // 获得指向的 namespace 名字，并添加到 configuration 的 cacheRefMap 中
            configuration.addCacheRef(builderAssistant.getCurrentNamespace(), context.getStringAttribute("namespace"));
            // 创建 CacheRefResolver 对象，并执行解析
            CacheRefResolver cacheRefResolver = new CacheRefResolver(builderAssistant, context.getStringAttribute("namespace"));
            try {
                cacheRefResolver.resolveCacheRef();
            } catch (IncompleteElementException e) {
                // 解析失败，添加到 configuration 的 incompleteCacheRefs 中
                configuration.addIncompleteCacheRef(cacheRefResolver);
            }
        }
    }

    // 解析 <cache /> 标签
    private void cacheElement(XNode context) throws Exception {
        if (context != null) {
            //解析<cache/>标签的type属性，这里我们可以自定义cache的实现类，比如redisCache，如果没有自定义，这里使用和一级缓存相同的PERPETUAL
            String type = context.getStringAttribute("type", "PERPETUAL");
            Class<? extends Cache> typeClass = typeAliasRegistry.resolveAlias(type);
            // 获得负责过期的 Cache 实现类
            String eviction = context.getStringAttribute("eviction", "LRU");
            Class<? extends Cache> evictionClass = typeAliasRegistry.resolveAlias(eviction);
            // 清空缓存的频率。0 代表不清空
            Long flushInterval = context.getLongAttribute("flushInterval");
            // 缓存容器大小
            Integer size = context.getIntAttribute("size");
            // 是否序列化
            boolean readWrite = !context.getBooleanAttribute("readOnly", false);
            // 是否阻塞
            boolean blocking = context.getBooleanAttribute("blocking", false);
            // 获得 Properties 属性
            Properties props = context.getChildrenAsProperties();
            // 创建 Cache 对象
            builderAssistant.useNewCache(typeClass, evictionClass, flushInterval, size, readWrite, blocking, props);
        }
    }

    // FROM 《MyBatis 官方文档 —— Mapper XML 文件》http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html
    // 已废弃！老式风格的参数映射。内联参数是首选,这个元素可能在将来被移除，这里不会记录。
    @Deprecated // add by 芋艿
    private void parameterMapElement(List<XNode> list) throws Exception {
        for (XNode parameterMapNode : list) {
            String id = parameterMapNode.getStringAttribute("id");
            String type = parameterMapNode.getStringAttribute("type");
            Class<?> parameterClass = resolveClass(type);
            List<XNode> parameterNodes = parameterMapNode.evalNodes("parameter");
            List<ParameterMapping> parameterMappings = new ArrayList<>();
            for (XNode parameterNode : parameterNodes) {
                String property = parameterNode.getStringAttribute("property");
                String javaType = parameterNode.getStringAttribute("javaType");
                String jdbcType = parameterNode.getStringAttribute("jdbcType");
                String resultMap = parameterNode.getStringAttribute("resultMap");
                String mode = parameterNode.getStringAttribute("mode");
                String typeHandler = parameterNode.getStringAttribute("typeHandler");
                Integer numericScale = parameterNode.getIntAttribute("numericScale");
                ParameterMode modeEnum = resolveParameterMode(mode);
                Class<?> javaTypeClass = resolveClass(javaType);
                JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
                @SuppressWarnings("unchecked")
                Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(typeHandler);
                ParameterMapping parameterMapping = builderAssistant.buildParameterMapping(parameterClass, property, javaTypeClass, jdbcTypeEnum, resultMap, modeEnum, typeHandlerClass, numericScale);
                parameterMappings.add(parameterMapping);
            }
            builderAssistant.addParameterMap(id, parameterClass, parameterMappings);
        }
    }

    // 解析 <resultMap /> 节点们
    private void resultMapElements(List<XNode> list) throws Exception {
        // 遍历 <resultMap /> 节点们
        for (XNode resultMapNode : list) {
            try {
                // 处理单个 <resultMap /> 节点
                resultMapElement(resultMapNode);
            } catch (IncompleteElementException e) {
                // ignore, it will be retried
            }
        }
    }

    // 解析 <resultMap /> 节点
    private ResultMap resultMapElement(XNode resultMapNode) throws Exception {
        return resultMapElement(resultMapNode, Collections.<ResultMapping>emptyList());
    }

    // 解析 <resultMap /> 节点
    private ResultMap resultMapElement(XNode resultMapNode, List<ResultMapping> additionalResultMappings) throws Exception {
        ErrorContext.instance().activity("processing " + resultMapNode.getValueBasedIdentifier());
        // 获得 id 属性
        String id = resultMapNode.getStringAttribute("id",
                resultMapNode.getValueBasedIdentifier());
        // 获得 type 属性
        String type = resultMapNode.getStringAttribute("type",
                resultMapNode.getStringAttribute("ofType",
                        resultMapNode.getStringAttribute("resultType",
                                resultMapNode.getStringAttribute("javaType"))));
        // 获得 extends 属性
        String extend = resultMapNode.getStringAttribute("extends");
        // 获得 autoMapping 属性
        Boolean autoMapping = resultMapNode.getBooleanAttribute("autoMapping");
        // 解析 type 对应的类
        Class<?> typeClass = resolveClass(type);
        Discriminator discriminator = null;
        // 创建 ResultMapping 集合
        List<ResultMapping> resultMappings = new ArrayList<>();
        resultMappings.addAll(additionalResultMappings);
        // 遍历 <resultMap /> 的子节点们
        List<XNode> resultChildren = resultMapNode.getChildren();
        for (XNode resultChild : resultChildren) {
            // 处理 <constructor /> 节点
            if ("constructor".equals(resultChild.getName())) {
                processConstructorElement(resultChild, typeClass, resultMappings);
            // 处理 <discriminator /> 节点
            } else if ("discriminator".equals(resultChild.getName())) {
                discriminator = processDiscriminatorElement(resultChild, typeClass, resultMappings);
            // 处理其它节点
            } else {
                List<ResultFlag> flags = new ArrayList<>();
                if ("id".equals(resultChild.getName())) {
                    flags.add(ResultFlag.ID);
                }
                resultMappings.add(buildResultMappingFromContext(resultChild, typeClass, flags));
            }
        }
        // 创建 ResultMapResolver 对象，执行解析
        ResultMapResolver resultMapResolver = new ResultMapResolver(builderAssistant, id, typeClass, extend, discriminator, resultMappings, autoMapping);
        try {
            return resultMapResolver.resolve();
        } catch (IncompleteElementException e) {
            // 解析失败，添加到 configuration 中
            configuration.addIncompleteResultMap(resultMapResolver);
            throw e;
        }
    }

    // 解析 <resultMap /> 节点的 <constructor /> 的节点
    private void processConstructorElement(XNode resultChild, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
        // 遍历 <constructor /> 的子节点们
        List<XNode> argChildren = resultChild.getChildren();
        for (XNode argChild : argChildren) {
            // 获得 ResultFlag 集合
            List<ResultFlag> flags = new ArrayList<>();
            flags.add(ResultFlag.CONSTRUCTOR);
            if ("idArg".equals(argChild.getName())) {
                flags.add(ResultFlag.ID);
            }
            // 将当前子节点构建成 ResultMapping 对象，并添加到 resultMappings 中
            resultMappings.add(buildResultMappingFromContext(argChild, resultType, flags));
        }
    }

    // 解析 <resultMap /> 节点的 <discriminator /> 的节点
    private Discriminator processDiscriminatorElement(XNode context, Class<?> resultType, List<ResultMapping> resultMappings) throws Exception {
        // 解析各种属性
        String column = context.getStringAttribute("column");
        String javaType = context.getStringAttribute("javaType");
        String jdbcType = context.getStringAttribute("jdbcType");
        String typeHandler = context.getStringAttribute("typeHandler");
        // 解析各种属性对应的类
        Class<?> javaTypeClass = resolveClass(javaType);
        Class<? extends TypeHandler<?>> typeHandlerClass = resolveClass(typeHandler);
        JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
        // 遍历 <discriminator /> 的子节点，解析成 discriminatorMap 集合
        Map<String, String> discriminatorMap = new HashMap<>();
        for (XNode caseChild : context.getChildren()) {
            String value = caseChild.getStringAttribute("value");
            String resultMap = caseChild.getStringAttribute("resultMap", processNestedResultMappings(caseChild, resultMappings));
            discriminatorMap.put(value, resultMap);
        }
        // 创建 Discriminator 对象
        return builderAssistant.buildDiscriminator(resultType, column, javaTypeClass, jdbcTypeEnum, typeHandlerClass, discriminatorMap);
    }

    private void sqlElement(List<XNode> list) throws Exception {
        if (configuration.getDatabaseId() != null) {
            sqlElement(list, configuration.getDatabaseId());
        }
        sqlElement(list, null);
        // 上面两块代码，可以简写成 sqlElement(list, configuration.getDatabaseId());
    }

    private void sqlElement(List<XNode> list, String requiredDatabaseId) throws Exception {
        // 遍历所有 <sql /> 节点
        for (XNode context : list) {
            // 获得 databaseId 属性
            String databaseId = context.getStringAttribute("databaseId");
            // 获得完整的 id 属性，格式为 `${namespace}.${id}` 。
            String id = context.getStringAttribute("id");
            id = builderAssistant.applyCurrentNamespace(id, false);
            // 判断 databaseId 是否匹配
            if (databaseIdMatchesCurrent(id, databaseId, requiredDatabaseId)) {
                // 添加到 sqlFragments 中
                sqlFragments.put(id, context);
            }
        }
    }

    private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
        // 如果不匹配，则返回 false
        if (requiredDatabaseId != null) {
            return requiredDatabaseId.equals(databaseId);
        } else {
            // 如果未设置 requiredDatabaseId ，但是 databaseId 存在，说明还是不匹配，则返回 false
            // mmp ，写的好绕
            if (databaseId != null) {
                return false;
            }
            // skip this fragment if there is a previous one with a not null databaseId
            // 判断是否已经存在
            if (this.sqlFragments.containsKey(id)) {
                XNode context = this.sqlFragments.get(id);
                // 若存在，则判断原有的 sqlFragment 是否 databaseId 为空。因为，当前 databaseId 为空，这样两者才能匹配。
                return context.getStringAttribute("databaseId") == null;
            }
        }
        return true;
    }

    // 将当前节点构建成 ResultMapping 对象
    private ResultMapping buildResultMappingFromContext(XNode context, Class<?> resultType, List<ResultFlag> flags) throws Exception {
        // 获得各种属性
        String property;
        if (flags.contains(ResultFlag.CONSTRUCTOR)) {
            property = context.getStringAttribute("name");
        } else {
            property = context.getStringAttribute("property");
        }
        String column = context.getStringAttribute("column");
        String javaType = context.getStringAttribute("javaType");
        String jdbcType = context.getStringAttribute("jdbcType");
        String nestedSelect = context.getStringAttribute("select");
        String nestedResultMap = context.getStringAttribute("resultMap",
                processNestedResultMappings(context, Collections.emptyList()));
        String notNullColumn = context.getStringAttribute("notNullColumn");
        String columnPrefix = context.getStringAttribute("columnPrefix");
        String typeHandler = context.getStringAttribute("typeHandler");
        String resultSet = context.getStringAttribute("resultSet");
        String foreignColumn = context.getStringAttribute("foreignColumn");
        boolean lazy = "lazy".equals(context.getStringAttribute("fetchType", configuration.isLazyLoadingEnabled() ? "lazy" : "eager"));
        // 获得各种属性对应的类
        Class<?> javaTypeClass = resolveClass(javaType);
        Class<? extends TypeHandler<?>> typeHandlerClass = resolveClass(typeHandler);
        JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);
        // 构建 ResultMapping 对象
        return builderAssistant.buildResultMapping(resultType, property, column, javaTypeClass, jdbcTypeEnum, nestedSelect, nestedResultMap, notNullColumn, columnPrefix, typeHandlerClass, flags, resultSet, foreignColumn, lazy);
    }

    // 处理内嵌的 ResultMap 的情况
    private String processNestedResultMappings(XNode context, List<ResultMapping> resultMappings) throws Exception {
        if ("association".equals(context.getName())
                || "collection".equals(context.getName())
                || "case".equals(context.getName())) {
            if (context.getStringAttribute("select") == null) {
                // 解析，并返回 ResultMap
                ResultMap resultMap = resultMapElement(context, resultMappings);
                return resultMap.getId();
            }
        }
        return null;
    }

    /**
     * 绑定 Mapper
     */
    private void bindMapperForNamespace() {
        String namespace = builderAssistant.getCurrentNamespace();
        if (namespace != null) {
            // 获得 Mapper 映射配置文件对应的 Mapper 接口，实际上类名就是 namespace 。嘿嘿，这个是常识。
            Class<?> boundType = null;
            try {
                boundType = Resources.classForName(namespace);
            } catch (ClassNotFoundException e) {
                //ignore, bound type is not required
            }
            if (boundType != null) {
                // 不存在该 Mapper 接口，则进行添加
                if (!configuration.hasMapper(boundType)) {
                    // Spring may not know the real resource name so we set a flag
                    // to prevent loading again this resource from the mapper interface
                    // look at MapperAnnotationBuilder#loadXmlResource
                    // 标记 namespace 已经添加，避免 MapperAnnotationBuilder#loadXmlResource(...) 重复加载
                    configuration.addLoadedResource("namespace:" + namespace);
                    // 添加到 configuration 中
                    configuration.addMapper(boundType);
                }
            }
        }
    }

}
