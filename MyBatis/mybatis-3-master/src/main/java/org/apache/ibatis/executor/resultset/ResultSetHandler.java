/**
 * Copyright 2009-2015 the original author or authors.
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
package org.apache.ibatis.executor.resultset;

import org.apache.ibatis.cursor.Cursor;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * {@link java.sql.ResultSet} 处理器接口
 *
 * @author Clinton Begin
 */
public interface ResultSetHandler {

    /**
     * 处理 {@link java.sql.ResultSet} 成映射的对应的结果
     *
     * @param stmt Statement 对象
     * @param <E> 泛型
     * @return 结果数组
     */
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;

    /**
     * 处理 {@link java.sql.ResultSet} 成 Cursor 对象
     *
     * @param stmt Statement 对象
     * @param <E> 泛型
     * @return Cursor 对象
     */
    <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;

    // 暂时忽略，和存储过程相关
    void handleOutputParameters(CallableStatement cs) throws SQLException;

}