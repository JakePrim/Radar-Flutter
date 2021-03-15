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
package org.apache.ibatis.reflection.property;

import org.apache.ibatis.reflection.ReflectionException;

import java.util.Locale;

/**
 * 属性名相关的工具类方法
 *
 * @author Clinton Begin
 */
public final class PropertyNamer {

    private PropertyNamer() {
        // Prevent Instantiation of Static Class
    }

    /**
     * 根据方法名，获得对应的属性名
     *
     * @param name 方法名
     * @return 属性名
     */
    public static String methodToProperty(String name) {
        // is 方法
        if (name.startsWith("is")) {
            name = name.substring(2);
        // get 或者 set 方法
        } else if (name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        // 抛出 ReflectionException 异常，因为只能处理 is、set、get 方法
        } else {
            throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
        }

        // 首字母小写
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    /**
     * 判断是否为 is、get、set 方法
     *
     * @param name 方法名
     * @return 是否
     */
    public static boolean isProperty(String name) {
        return name.startsWith("get") || name.startsWith("set") || name.startsWith("is");
    }

    /**
     * 判断是否为 get、is 方法
     *
     * @param name 方法名
     * @return 是否
     */
    public static boolean isGetter(String name) {
        return name.startsWith("get") || name.startsWith("is");
    }

    /**
     * 判断是否为 set 方法
     *
     * @param name 方法名
     * @return 是否
     */
    public static boolean isSetter(String name) {
        return name.startsWith("set");
    }

}