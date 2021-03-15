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
package org.apache.ibatis.scripting.xmltags;

import org.apache.ibatis.builder.BuilderException;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OGNL 表达式计算器
 *
 * @author Clinton Begin
 */
public class ExpressionEvaluator {

    /**
     * 判断表达式对应的值，是否为 true
     *
     * @param expression 表达式
     * @param parameterObject 参数对象
     * @return 是否为 true
     */
    public boolean evaluateBoolean(String expression, Object parameterObject) {
        // 获得表达式对应的值
        Object value = OgnlCache.getValue(expression, parameterObject);
        // 如果是 Boolean 类型，直接判断
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        // 如果是 Number 类型，则判断不等于 0
        if (value instanceof Number) {
            return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
        }
        // 如果是其它类型，判断非空
        return value != null;
    }

    /**
     * 获得表达式对应的集合
     *
     * @param expression 表达式
     * @param parameterObject 参数对象
     * @return 迭代器对象
     */
    public Iterable<?> evaluateIterable(String expression, Object parameterObject) {
        // 获得表达式对应的值
        Object value = OgnlCache.getValue(expression, parameterObject);
        if (value == null) {
            throw new BuilderException("The expression '" + expression + "' evaluated to a null value.");
        }
        // 如果是 Iterable 类型，直接返回
        if (value instanceof Iterable) {
            return (Iterable<?>) value;
        }
        // 如果是数组类型，则返回数组
        if (value.getClass().isArray()) {
            // the array may be primitive, so Arrays.asList() may throw
            // a ClassCastException (issue 209).  Do the work manually
            // Curse primitives! :) (JGB)
            int size = Array.getLength(value);
            List<Object> answer = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Object o = Array.get(value, i);
                answer.add(o);
            }
            return answer;
        }
        // 如果是 Map 类型，则返回 Map.entrySet 集合
        if (value instanceof Map) {
            return ((Map) value).entrySet();
        }
        throw new BuilderException("Error evaluating expression '" + expression + "'.  Return value (" + value + ") was not iterable.");
    }

}