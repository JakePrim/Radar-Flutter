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
package org.apache.ibatis.reflection;

import org.apache.ibatis.reflection.invoker.GetFieldInvoker;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.invoker.MethodInvoker;
import org.apache.ibatis.reflection.property.PropertyTokenizer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 类的元数据。
 *
 * 基于 {@link Reflector} 和 {@link PropertyTokenizer} ，提供对指定类的各种操作
 *
 * @author Clinton Begin
 */
public class MetaClass {

    private final ReflectorFactory reflectorFactory;
    private final Reflector reflector;

    private MetaClass(Class<?> type, ReflectorFactory reflectorFactory) {
        this.reflectorFactory = reflectorFactory;
        this.reflector = reflectorFactory.findForClass(type);
    }

    public static MetaClass forClass(Class<?> type, ReflectorFactory reflectorFactory) {
        return new MetaClass(type, reflectorFactory);
    }

    /**
     * 创建类的指定属性的类的 MetaClass 对象
     *
     * @param name 指定属性
     * @return MetaClass 对象
     */
    public MetaClass metaClassForProperty(String name) {
        // 获得属性的类
        Class<?> propType = reflector.getGetterType(name);
        // 创建 MetaClass 对象
        return MetaClass.forClass(propType, reflectorFactory);
    }

    public String findProperty(String name) {
        // 构建属性
        StringBuilder prop = buildProperty(name, new StringBuilder());
        return prop.length() > 0 ? prop.toString() : null;
    }

    /**
     * 获得属性
     *
     * @param name 属性名
     * @param useCamelCaseMapping 是否下划线转驼峰
     * @return 属性结果
     */
    public String findProperty(String name, boolean useCamelCaseMapping) {
        // 下划线转驼峰
        if (useCamelCaseMapping) {
            name = name.replace("_", "");
        }
        // 获得属性
        return findProperty(name);
    }

    public String[] getGetterNames() {
        return reflector.getGetablePropertyNames();
    }

    public String[] getSetterNames() {
        return reflector.getSetablePropertyNames();
    }

    /**
     * 获得指定属性的 setting 方法的参数类型
     *
     * 和 {@link #getGetterType(String)} 方法的思路类似
     *
     * @param name 属性
     * @return 方法参数的类型
     */
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaClass metaProp = metaClassForProperty(prop.getName());
            return metaProp.getSetterType(prop.getChildren());
        } else {
            return reflector.getSetterType(prop.getName());
        }
    }

    /**
     * 获得指定属性的 getting 方法的返回值的类型
     *
     * @param name 属性
     * @return 返回值的类型
     */
    public Class<?> getGetterType(String name) {
        // 创建 PropertyTokenizer 对象，对 name 进行分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 创建 MetaClass 对象
            MetaClass metaProp = metaClassForProperty(prop);
            // 递归判断子表达式 children ，获得返回值的类型
            return metaProp.getGetterType(prop.getChildren());
        }
        // issue #506. Resolve the type inside a Collection Object
        // 直接获得返回值的类型
        return getGetterType(prop);
    }

    private MetaClass metaClassForProperty(PropertyTokenizer prop) {
        // 【调用】获得 getting 方法返回的类型
        Class<?> propType = getGetterType(prop);
        // 创建 MetaClass 对象
        return MetaClass.forClass(propType, reflectorFactory);
    }

    private Class<?> getGetterType(PropertyTokenizer prop) {
        // 获得返回类型
        Class<?> type = reflector.getGetterType(prop.getName());
        // 如果获取数组的某个位置的元素，则获取其泛型。例如说：list[0].field ，那么就会解析 list 是什么类型，这样才好通过该类型，继续获得 field
        if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
            // 【调用】获得返回的类型
            Type returnType = getGenericGetterType(prop.getName());
            // 如果是泛型，进行解析真正的类型
            if (returnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) { // 为什么这里判断大小为 1 呢，因为 Collection 是 Collection<T> ，至多一个。
                    returnType = actualTypeArguments[0];
                    if (returnType instanceof Class) {
                        type = (Class<?>) returnType;
                    } else if (returnType instanceof ParameterizedType) {
                        type = (Class<?>) ((ParameterizedType) returnType).getRawType();
                    }
                }
            }
        }
        return type;
    }

    private Type getGenericGetterType(String propertyName) {
        try {
            // 获得 Invoker 对象
            Invoker invoker = reflector.getGetInvoker(propertyName);
            // 如果 MethodInvoker 对象，则说明是 getting 方法，解析方法返回类型
            if (invoker instanceof MethodInvoker) {
                Field _method = MethodInvoker.class.getDeclaredField("method");
                _method.setAccessible(true);
                Method method = (Method) _method.get(invoker);
                return TypeParameterResolver.resolveReturnType(method, reflector.getType());
            // 如果 GetFieldInvoker 对象，则说明是 field ，直接访问
            } else if (invoker instanceof GetFieldInvoker) {
                Field _field = GetFieldInvoker.class.getDeclaredField("field");
                _field.setAccessible(true);
                Field field = (Field) _field.get(invoker);
                return TypeParameterResolver.resolveFieldType(field, reflector.getType());
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
        return null;
    }

    /**
     * 判断指定属性是否有 setting 方法
     *
     * 思路 {@link #hasGetter(String)} 方法类似
     *
     * @param name 属性
     * @return 是否
     */
    public boolean hasSetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (reflector.hasSetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop.getName());
                return metaProp.hasSetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasSetter(prop.getName());
        }
    }

    /**
     * 判断指定属性是否有 getting 方法
     *
     * @param name 属性
     * @return 是否有
     */
    public boolean hasGetter(String name) {
        // 创建 PropertyTokenizer 对象，对 name 进行分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 判断是否有该属性的 getting 方法
            if (reflector.hasGetter(prop.getName())) {
                // 创建 MetaClass 对象
                MetaClass metaProp = metaClassForProperty(prop);
                // 递归判断子表达式 children ，是否有 getting 方法
                return metaProp.hasGetter(prop.getChildren());
            } else {
                return false;
            }
        // 无子表达式
        } else {
            // 判断是否有该属性的 getting 方法
            return reflector.hasGetter(prop.getName());
        }
    }

    public Invoker getGetInvoker(String name) {
        return reflector.getGetInvoker(name);
    }

    public Invoker getSetInvoker(String name) {
        return reflector.getSetInvoker(name);
    }

    /**
     * 构建属性
     *
     * @param name 属性名
     * @param builder 字符串结果
     * @return 最终结果
     */
    private StringBuilder buildProperty(String name, StringBuilder builder) {
        // 创建 PropertyTokenizer 对象，对 name 进行分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 获得属性名，并添加到 builder 中
            String propertyName = reflector.findPropertyName(prop.getName());
            if (propertyName != null) {
                // 拼接属性到 builder 中
                builder.append(propertyName);
                builder.append(".");
                // 创建 MetaClass 对象
                MetaClass metaProp = metaClassForProperty(propertyName);
                // 递归解析子表达式 children ，并将结果添加到 builder 中
                metaProp.buildProperty(prop.getChildren(), builder);
            }
        // 无子表达式
        } else {
            // 获得属性名，并添加到 builder 中
            String propertyName = reflector.findPropertyName(name);
            if (propertyName != null) {
                builder.append(propertyName);
            }
        }
        return builder;
    }

    public boolean hasDefaultConstructor() {
        return reflector.hasDefaultConstructor();
    }

}
