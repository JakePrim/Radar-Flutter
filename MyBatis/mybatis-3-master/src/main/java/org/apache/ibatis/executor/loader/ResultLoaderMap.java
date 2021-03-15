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
package org.apache.ibatis.executor.loader;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.SQLException;
import java.util.*;

/**
 * {@link ResultLoader} 的映射。
 *
 * 该映射，最终创建代理对象时，会作为参数传入代理。
 *
 * @author Clinton Begin
 * @author Franta Mejta
 */
public class ResultLoaderMap {

    /**
     * LoadPair 的映射
     */
    private final Map<String, LoadPair> loaderMap = new HashMap<>();

    /**
     * 添加到 loaderMap 中
     *
     * @param property 属性名
     * @param metaResultObject 结果对象的 MetaObject 对象
     * @param resultLoader ResultLoader 对象
     */
    public void addLoader(String property, MetaObject metaResultObject, ResultLoader resultLoader) {
        String upperFirst = getUppercaseFirstProperty(property);
        // 已存在，则抛出 ExecutorException 异常
        if (!upperFirst.equalsIgnoreCase(property) && loaderMap.containsKey(upperFirst)) {
            throw new ExecutorException("Nested lazy loaded result property '" + property +
                    "' for query id '" + resultLoader.mappedStatement.getId() +
                    " already exists in the result map. The leftmost property of all lazy loaded properties must be unique within a result map.");
        }
        // 创建 LoadPair 对象，添加到 loaderMap 中
        loaderMap.put(upperFirst, new LoadPair(property, metaResultObject, resultLoader));
    }

    public final Map<String, LoadPair> getProperties() {
        return new HashMap<>(this.loaderMap);
    }

    public Set<String> getPropertyNames() {
        return loaderMap.keySet();
    }

    public int size() {
        return loaderMap.size();
    }

    public boolean hasLoader(String property) {
        return loaderMap.containsKey(property.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 执行指定属性的加载
     *
     * @param property 属性
     * @return 加载是否成功
     */
    public boolean load(String property) throws SQLException {
        // 获得 LoadPair 对象，并移除
        LoadPair pair = loaderMap.remove(property.toUpperCase(Locale.ENGLISH));
        // 执行加载
        if (pair != null) {
            pair.load();
            return true; // 加载成功
        }
        return false; // 加载失败
    }

    public void remove(String property) {
        loaderMap.remove(property.toUpperCase(Locale.ENGLISH));
    }

    /**
     * 执行所有属性的加载
     */
    public void loadAll() throws SQLException {
        // 遍历 loaderMap 属性
        final Set<String> methodNameSet = loaderMap.keySet();
        String[] methodNames = methodNameSet.toArray(new String[methodNameSet.size()]);
        for (String methodName : methodNames) {
            // 执行加载
            load(methodName);
        }
    }

    /**
     * 使用 . 分隔属性，并获得首个字符串，并大写
     *
     * @param property 属性
     * @return 字符串 + 大写
     */
    private static String getUppercaseFirstProperty(String property) {
        String[] parts = property.split("\\.");
        return parts[0].toUpperCase(Locale.ENGLISH);
    }

    /**
     * Property which was not loaded yet.
     */
    public static class LoadPair implements Serializable {

        private static final long serialVersionUID = 20130412;

        /**
         * Name of factory method which returns database connection.
         */
        private static final String FACTORY_METHOD = "getConfiguration";
        /**
         * Object to check whether we went through serialization..
         */
        private final transient Object serializationCheck = new Object();
        /**
         * Meta object which sets loaded properties.
         */
        private transient MetaObject metaResultObject;
        /**
         * Result loader which loads unread properties.
         */
        private transient ResultLoader resultLoader;
        /**
         * Wow, logger.
         */
        private transient Log log;
        /**
         * Factory class through which we get database connection.
         */
        private Class<?> configurationFactory;
        /**
         * Name of the unread property.
         */
        private String property;
        /**
         * ID of SQL statement which loads the property.
         */
        private String mappedStatement;
        /**
         * Parameter of the sql statement.
         */
        private Serializable mappedParameter;

        private LoadPair(final String property, MetaObject metaResultObject, ResultLoader resultLoader) {
            this.property = property;
            this.metaResultObject = metaResultObject;
            this.resultLoader = resultLoader;

            /* Save required information only if original object can be serialized. */
            // 当 `metaResultObject.originalObject` 可序列化时，则记录 mappedStatement、mappedParameter、configurationFactory 属性
            if (metaResultObject != null && metaResultObject.getOriginalObject() instanceof Serializable) {
                final Object mappedStatementParameter = resultLoader.parameterObject;

                /* @todo May the parameter be null? */
                if (mappedStatementParameter instanceof Serializable) {
                    this.mappedStatement = resultLoader.mappedStatement.getId();
                    this.mappedParameter = (Serializable) mappedStatementParameter;

                    this.configurationFactory = resultLoader.configuration.getConfigurationFactory();
                } else {
                    Log log = this.getLogger();
                    if (log.isDebugEnabled()) {
                        log.debug("Property [" + this.property + "] of ["
                                + metaResultObject.getOriginalObject().getClass() + "] cannot be loaded "
                                + "after deserialization. Make sure it's loaded before serializing "
                                + "forenamed object.");
                    }
                }
            }
        }

        public void load() throws SQLException {
            /* These field should not be null unless the loadpair was serialized.
             * Yet in that case this method should not be called. */
            // 若 metaResultObject 或 resultLoader 为空，抛出 IllegalArgumentException 异常
            if (this.metaResultObject == null) {
                throw new IllegalArgumentException("metaResultObject is null");
            }
            if (this.resultLoader == null) {
                throw new IllegalArgumentException("resultLoader is null");
            }

            // 执行加载
            this.load(null);
        }

        public void load(final Object userObject) throws SQLException {
            if (this.metaResultObject == null || this.resultLoader == null) {
                if (this.mappedParameter == null) {
                    throw new ExecutorException("Property [" + this.property + "] cannot be loaded because "
                            + "required parameter of mapped statement ["
                            + this.mappedStatement + "] is not serializable.");
                }

                // 获得 Configuration 对象
                final Configuration config = this.getConfiguration();
                // 获得 MappedStatement 对象
                final MappedStatement ms = config.getMappedStatement(this.mappedStatement);
                if (ms == null) {
                    throw new ExecutorException("Cannot lazy load property [" + this.property
                            + "] of deserialized object [" + userObject.getClass()
                            + "] because configuration does not contain statement ["
                            + this.mappedStatement + "]");
                }

                // 获得对应的 MetaObject 对象
                this.metaResultObject = config.newMetaObject(userObject);
                // 创建 ResultLoader 对象
                this.resultLoader = new ResultLoader(config, new ClosedExecutor(), ms, this.mappedParameter,
                        metaResultObject.getSetterType(this.property), null, null);
            }

            /* We are using a new executor because we may be (and likely are) on a new thread
             * and executors aren't thread safe. (Is this sufficient?)
             *
             * A better approach would be making executors thread safe. */
            if (this.serializationCheck == null) {
                final ResultLoader old = this.resultLoader;
                this.resultLoader = new ResultLoader(old.configuration, new ClosedExecutor(), old.mappedStatement,
                        old.parameterObject, old.targetType, old.cacheKey, old.boundSql);
            }

            this.metaResultObject.setValue(property, this.resultLoader.loadResult());
        }

        private Configuration getConfiguration() {
            if (this.configurationFactory == null) {
                throw new ExecutorException("Cannot get Configuration as configuration factory was not set.");
            }

            Object configurationObject;
            try {
                final Method factoryMethod = this.configurationFactory.getDeclaredMethod(FACTORY_METHOD);
                if (!Modifier.isStatic(factoryMethod.getModifiers())) {
                    throw new ExecutorException("Cannot get Configuration as factory method ["
                            + this.configurationFactory + "]#["
                            + FACTORY_METHOD + "] is not static.");
                }

                if (!factoryMethod.isAccessible()) {
                    configurationObject = AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                        @Override
                        public Object run() throws Exception {
                            try {
                                factoryMethod.setAccessible(true);
                                return factoryMethod.invoke(null);
                            } finally {
                                factoryMethod.setAccessible(false);
                            }
                        }
                    });
                } else {
                    configurationObject = factoryMethod.invoke(null);
                }
            } catch (final ExecutorException ex) {
                throw ex;
            } catch (final NoSuchMethodException ex) {
                throw new ExecutorException("Cannot get Configuration as factory class ["
                        + this.configurationFactory + "] is missing factory method of name ["
                        + FACTORY_METHOD + "].", ex);
            } catch (final PrivilegedActionException ex) {
                throw new ExecutorException("Cannot get Configuration as factory method ["
                        + this.configurationFactory + "]#["
                        + FACTORY_METHOD + "] threw an exception.", ex.getCause());
            } catch (final Exception ex) {
                throw new ExecutorException("Cannot get Configuration as factory method ["
                        + this.configurationFactory + "]#["
                        + FACTORY_METHOD + "] threw an exception.", ex);
            }

            if (!(configurationObject instanceof Configuration)) {
                throw new ExecutorException("Cannot get Configuration as factory method ["
                        + this.configurationFactory + "]#["
                        + FACTORY_METHOD + "] didn't return [" + Configuration.class + "] but ["
                        + (configurationObject == null ? "null" : configurationObject.getClass()) + "].");
            }

            return Configuration.class.cast(configurationObject);
        }

        private Log getLogger() {
            if (this.log == null) {
                this.log = LogFactory.getLog(this.getClass());
            }
            return this.log;
        }
    }

    /**
     * 已经关闭的 Executor 实现类
     */
    private static final class ClosedExecutor extends BaseExecutor {

        public ClosedExecutor() {
            super(null, null);
        }

        @Override
        public boolean isClosed() {
            return true;
        }

        @Override
        protected int doUpdate(MappedStatement ms, Object parameter) throws SQLException {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        protected List<BatchResult> doFlushStatements(boolean isRollback) throws SQLException {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        protected <E> List<E> doQuery(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
            throw new UnsupportedOperationException("Not supported.");
        }

        @Override
        protected <E> Cursor<E> doQueryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds, BoundSql boundSql) throws SQLException {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

}