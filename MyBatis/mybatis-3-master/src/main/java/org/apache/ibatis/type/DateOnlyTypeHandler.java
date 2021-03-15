/**
 * Copyright 2009-2017 the original author or authors.
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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Date 类型的 TypeHandler 实现类
 *
 * @author Clinton Begin
 */
public class DateOnlyTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
            throws SQLException {
        // 将 java Date 转换成 sql Date 类型
        ps.setDate(i, new java.sql.Date(parameter.getTime()));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // 获得 sql Date 的值
        java.sql.Date sqlDate = rs.getDate(columnName);
        // 将 sql Date 转换成 java Date 类型
        if (sqlDate != null) {
            return new Date(sqlDate.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // 获得 sql Date 的值
        java.sql.Date sqlDate = rs.getDate(columnIndex);
        // 将 sql Date 转换成 java Date 类型
        if (sqlDate != null) {
            return new Date(sqlDate.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // 获得 sql Date 的值
        java.sql.Date sqlDate = cs.getDate(columnIndex);
        // 将 sql Date 转换成 java Date 类型
        if (sqlDate != null) {
            return new Date(sqlDate.getTime());
        }
        return null;
    }

}