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
package org.apache.ibatis.plugin;

import org.apache.ibatis.reflection.ExceptionUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 插件类，一方面提供创建动态代理对象的方法，另一方面实现对指定类的指定方法的拦截处理。
 *
 * @author Clinton Begin
 */
public class Plugin implements InvocationHandler {

    /**
     * 目标对象
     */
    private final Object target;
    /**
     * 拦截器
     */
    private final Interceptor interceptor;
    /**
     * 拦截的方法映射
     *
     * KEY：类
     * VALUE：方法集合
     */
    private final Map<Class<?>, Set<Method>> signatureMap;

    private Plugin(Object target, Interceptor interceptor, Map<Class<?>, Set<Method>> signatureMap) {
        this.target = target;
        this.interceptor = interceptor;
        this.signatureMap = signatureMap;
    }

    /**
     * 创建目标类的代理对象
     *
     * @param target 目标类
     * @param interceptor 拦截器对象
     * @return 代理对象
     */
    public static Object wrap(Object target, Interceptor interceptor) {
        // 获得拦截的方法映射
        Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
        // 获得目标类的类型
        Class<?> type = target.getClass();
        // 获得目标类的接口集合
        Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
        // 若有接口，则创建目标对象的 JDK Proxy 对象
        if (interfaces.length > 0) {
            return Proxy.newProxyInstance(
                    type.getClassLoader(),
                    interfaces,
                    new Plugin(target, interceptor, signatureMap)); // 因为 Plugin 实现了 InvocationHandler 接口，所以可以作为 JDK 动态代理的调用处理器
        }
        // 如果没有，则返回原始的目标对象
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            // 获得目标方法是否被拦截
            Set<Method> methods = signatureMap.get(method.getDeclaringClass());
            if (methods != null && methods.contains(method)) {
                // 如果是，则拦截处理该方法
                return interceptor.intercept(new Invocation(target, method, args));
            }
            // 如果不是，则调用原方法
            return method.invoke(target, args);
        } catch (Exception e) {
            throw ExceptionUtil.unwrapThrowable(e);
        }
    }

    private static Map<Class<?>, Set<Method>> getSignatureMap(Interceptor interceptor) {
        Intercepts interceptsAnnotation = interceptor.getClass().getAnnotation(Intercepts.class);
        // issue #251
        if (interceptsAnnotation == null) {
            throw new PluginException("No @Intercepts annotation was found in interceptor " + interceptor.getClass().getName());
        }
        Signature[] sigs = interceptsAnnotation.value();
        Map<Class<?>, Set<Method>> signatureMap = new HashMap<>();
        for (Signature sig : sigs) {
            Set<Method> methods = signatureMap.computeIfAbsent(sig.type(), k -> new HashSet<>());
            try {
                Method method = sig.type().getMethod(sig.method(), sig.args());
                methods.add(method);
            } catch (NoSuchMethodException e) {
                throw new PluginException("Could not find method on " + sig.type() + " named " + sig.method() + ". Cause: " + e, e);
            }
        }
        return signatureMap;
    }

    private static Class<?>[] getAllInterfaces(Class<?> type, Map<Class<?>, Set<Method>> signatureMap) {
        // 接口的集合
        Set<Class<?>> interfaces = new HashSet<>();
        // 循环递归 type 类，机器父类
        while (type != null) {
            // 遍历接口集合，若在 signatureMap 中，则添加到 interfaces 中
            for (Class<?> c : type.getInterfaces()) {
                if (signatureMap.containsKey(c)) {
                    interfaces.add(c);
                }
            }
            // 获得父类
            type = type.getSuperclass();
        }
        // 创建接口的数组
        return interfaces.toArray(new Class<?>[interfaces.size()]);
    }

}
