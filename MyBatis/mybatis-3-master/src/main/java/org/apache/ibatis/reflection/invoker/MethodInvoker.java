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
package org.apache.ibatis.reflection.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 指定方法的调用器
 *
 * @author Clinton Begin
 */
public class MethodInvoker implements Invoker {

    /**
     * 类型
     */
    private final Class<?> type;
    /**
     * 指定方法
     */
    private final Method method;

    public MethodInvoker(Method method) {
        this.method = method;

        // 参数大小为 1 时，一般是 setting 方法，设置 type 为方法参数[0]
        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        // 否则，一般是 getting 方法，设置 type 为返回类型
        } else {
            type = method.getReturnType();
        }
    }

    // 执行指定方法
    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(target, args);
    }

    @Override
    public Class<?> getType() {
        return type;
    }

}