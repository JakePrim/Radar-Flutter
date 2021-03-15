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

import java.sql.*;
import java.util.Date;

/**
 * Date 类型的 TypeHandler 实现类
 *
 * @author Clinton Begin
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
            throws SQLException {
        // 将 Date 转换成 Timestamp 类型
        // 然后设置到 ps 中
        ps.setTimestamp(i, new Timestamp(parameter.getTime()));
    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // 获得 Timestamp 的值
        Timestamp sqlTimestamp = rs.getTimestamp(columnName);
        // 将 Timestamp 转换成 Date 类型
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // 获得 Timestamp 的值
        Timestamp sqlTimestamp = rs.getTimestamp(columnIndex);
        // 将 Timestamp 转换成 Date 类型
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // 获得 Timestamp 的值
        Timestamp sqlTimestamp = cs.getTimestamp(columnIndex);
        // 将 Timestamp 转换成 Date 类型
        if (sqlTimestamp != null) {
            return new Date(sqlTimestamp.getTime());
        }
        return null;
    }

}