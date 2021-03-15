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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Integer 类型的 TypeHandler 实现类
 *
 * @author Clinton Begin
 */
public class IntegerTypeHandler extends BaseTypeHandler<Integer> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType)
            throws SQLException {
        // 直接设置参数即可
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        // 获得字段的值
        int result = rs.getInt(columnName);
        // 先通过 rs 判断是否空，如果是空，则返回 null ，否则返回 result
        return (result == 0 && rs.wasNull()) ? null : result;
    }

    @Override
    public Integer getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        // 获得字段的值
        int result = rs.getInt(columnIndex);
        // 先通过 rs 判断是否空，如果是空，则返回 null ，否则返回 result
        return (result == 0 && rs.wasNull()) ? null : result;
    }

    @Override
    public Integer getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        // 获得字段的值
        int result = cs.getInt(columnIndex);
        // 先通过 cs 判断是否空，如果是空，则返回 null ，否则返回 result
        return (result == 0 && cs.wasNull()) ? null : result;
    }
}