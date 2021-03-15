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
package org.apache.ibatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * A parameter handler sets the parameters of the {@code PreparedStatement}
 *
 * 参数处理器接口
 *
 * @author Clinton Begin
 */
public interface ParameterHandler {

    /**
     * @return 参数对象
     */
    Object getParameterObject();

    /**
     * 设置 PreparedStatement 的占位符参数
     *
     * @param ps PreparedStatement 对象
     * @throws SQLException 发生 SQL 异常时
     */
    void setParameters(PreparedStatement ps) throws SQLException;

}