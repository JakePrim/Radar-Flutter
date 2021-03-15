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

/**
 * `<if />` 标签的 SqlNode 实现类
 *
 * @author Clinton Begin
 */
public class IfSqlNode implements SqlNode {

    private final ExpressionEvaluator evaluator;
    /**
     * 判断表达式
     */
    private final String test;
    /**
     * 内嵌的 SqlNode 节点
     */
    private final SqlNode contents;

    public IfSqlNode(SqlNode contents, String test) {
        this.test = test;
        this.contents = contents;
        this.evaluator = new ExpressionEvaluator();
    }

    @Override
    public boolean apply(DynamicContext context) {
        // 判断是否符合条件
        if (evaluator.evaluateBoolean(test, context.getBindings())) {
            // 符合，执行 contents 的应用
            contents.apply(context);
            // 返回成功
            return true;
        }
        // 不符合，返回失败
        return false;
    }

}