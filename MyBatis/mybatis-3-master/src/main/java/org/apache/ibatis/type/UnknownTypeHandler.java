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
package org.apache.ibatis.type;

import org.apache.ibatis.io.Resources;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 未知的 TypeHandler 实现类
 *
 * 通过获取对应的 TypeHandler ，进行处理。
 *
 * @author Clinton Begin
 */
public class UnknownTypeHandler extends BaseTypeHandler<Object> {

    /**
     * ObjectTypeHandler 单例
     */
    private static final ObjectTypeHandler OBJECT_TYPE_HANDLER = new ObjectTypeHandler();

    /**
     * TypeHandler 注册表
     */
    private TypeHandlerRegistry typeHandlerRegistry;

    public UnknownTypeHandler(TypeHandlerRegistry typeHandlerRegistry) {
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
            throws SQLException {
        // 获得参数对应的处理器
        TypeHandler handler = resolveTypeHandler(parameter, jdbcType); // <1>
        // 使用 handler 设置参数
        handler.setParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // 获得参数对应的处理器
        TypeHandler<?> handler = resolveTypeHandler(rs, columnName); // <2>
        // 使用 handler 获得值
        return handler.getResult(rs, columnName);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // 获得参数对应的处理器
        TypeHandler<?> handler = resolveTypeHandler(rs.getMetaData(), columnIndex); // <3>
        // 如果找不到对应的处理器，使用 OBJECT_TYPE_HANDLER
        if (handler == null || handler instanceof UnknownTypeHandler) {
            handler = OBJECT_TYPE_HANDLER;
        }
        // 使用 handler 获得值
        return handler.getResult(rs, columnIndex);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getObject(columnIndex);
    }

    private TypeHandler<? extends Object> resolveTypeHandler(Object parameter, JdbcType jdbcType) { // <1>
        TypeHandler<? extends Object> handler;
        // 参数为空，返回 OBJECT_TYPE_HANDLER
        if (parameter == null) {
            handler = OBJECT_TYPE_HANDLER;
        // 参数非空，使用参数类型获得对应的 TypeHandler
        } else {
            handler = typeHandlerRegistry.getTypeHandler(parameter.getClass(), jdbcType);
            // check if handler is null (issue #270)
            // 获取不到，则使用 OBJECT_TYPE_HANDLER
            if (handler == null || handler instanceof UnknownTypeHandler) {
                handler = OBJECT_TYPE_HANDLER;
            }
        }
        return handler;
    }

    private TypeHandler<?> resolveTypeHandler(ResultSet rs, String column) {
        try {
            // 获得 columnIndex
            Map<String, Integer> columnIndexLookup = new HashMap<>();
            ResultSetMetaData rsmd = rs.getMetaData(); // 通过 metaData
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String name = rsmd.getColumnName(i);
                columnIndexLookup.put(name, i);
            }
            Integer columnIndex = columnIndexLookup.get(column);
            TypeHandler<?> handler = null;
            // 首先，通过 columnIndex 获得 TypeHandler
            if (columnIndex != null) {
                handler = resolveTypeHandler(rsmd, columnIndex); // <3>
            }
            // 获得不到，使用 OBJECT_TYPE_HANDLER
            if (handler == null || handler instanceof UnknownTypeHandler) {
                handler = OBJECT_TYPE_HANDLER;
            }
            return handler;
        } catch (SQLException e) {
            throw new TypeException("Error determining JDBC type for column " + column + ".  Cause: " + e, e);
        }
    }

    private TypeHandler<?> resolveTypeHandler(ResultSetMetaData rsmd, Integer columnIndex) { // <3>
        TypeHandler<?> handler = null;
        // 获得 JDBC Type 类型
        JdbcType jdbcType = safeGetJdbcTypeForColumn(rsmd, columnIndex);
        // 获得 Java Type 类型
        Class<?> javaType = safeGetClassForColumn(rsmd, columnIndex);
        //获得对应的 TypeHandler 对象
        if (javaType != null && jdbcType != null) {
            handler = typeHandlerRegistry.getTypeHandler(javaType, jdbcType);
        } else if (javaType != null) {
            handler = typeHandlerRegistry.getTypeHandler(javaType);
        } else if (jdbcType != null) {
            handler = typeHandlerRegistry.getTypeHandler(jdbcType);
        }
        return handler;
    }

    private JdbcType safeGetJdbcTypeForColumn(ResultSetMetaData rsmd, Integer columnIndex) {
        try {
            // 从 ResultSetMetaData 中，获得字段类型
            // 获得 JDBC Type
            return JdbcType.forCode(rsmd.getColumnType(columnIndex));
        } catch (Exception e) {
            return null;
        }
    }

    private Class<?> safeGetClassForColumn(ResultSetMetaData rsmd, Integer columnIndex) {
        try {
            // 从 ResultSetMetaData 中，获得字段类型
            // 获得 Java Type
            return Resources.classForName(rsmd.getColumnClassName(columnIndex));
        } catch (Exception e) {
            return null;
        }
    }

}