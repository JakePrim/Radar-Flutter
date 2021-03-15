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
package org.apache.ibatis.scripting.xmltags;

import java.util.List;

/**
 * `<choose />` 标签的 SqlNode 实现类
 *
 * @author Clinton Begin
 */
public class ChooseSqlNode implements SqlNode {

    /**
     * <otherwise /> 标签对应的 SqlNode 节点
     */
    private final SqlNode defaultSqlNode;
    /**
     * <when /> 标签对应的 SqlNode 节点数组
     */
    private final List<SqlNode> ifSqlNodes;

    public ChooseSqlNode(List<SqlNode> ifSqlNodes, SqlNode defaultSqlNode) {
        this.ifSqlNodes = ifSqlNodes;
        this.defaultSqlNode = defaultSqlNode;
    }

    @Override
    public boolean apply(DynamicContext context) {
        // 先判断  <when /> 标签中，是否有符合条件的节点。
        // 如果有，则进行应用。并且只因应用一个 SqlNode 对象
        for (SqlNode sqlNode : ifSqlNodes) {
            if (sqlNode.apply(context)) {
                return true;
            }
        }
        // 再判断  <otherwise /> 标签，是否存在
        // 如果存在，则进行应用
        if (defaultSqlNode != null) {
            defaultSqlNode.apply(context);
            return true;
        }
        // 返回都失败
        return false;
    }

}