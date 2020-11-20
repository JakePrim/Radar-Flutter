package org.prim.ioc.demo3;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author prim
 * 配置后Spring 会自动调用
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("第五步：初始化前方法...");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        System.out.println("第八步：初始化后方法...");
        /**
         * 增强类 动态代理
         */
        if ("userDao".equals(beanName)) {
            //增强类 实现接口类
            Object o = Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if ("save".equals(method.getName())) {
                                System.out.println("proxy = 权限校验");
                                return method.invoke(bean, args);
                            }
                            return method.invoke(bean, args);
                        }
                    });
            return o;
        }

        return bean;
    }
}
