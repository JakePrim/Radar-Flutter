package com.prim.aop;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 使用CGLIB 代理
 */
public class MyCglibProxy implements MethodInterceptor {

    private Object targetObj;

    public MyCglibProxy(Object obj) {
        this.targetObj = obj;
    }

    public Object createProxy() {
        //创建核心类
        Enhancer enhancer = new Enhancer();
        //设置父类//通过父类 目标类 产生子类
        enhancer.setSuperclass(targetObj.getClass());
        //设置回调
        enhancer.setCallback(this);
        //生成代理
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if ("save".equals(method.getName())) {
            System.out.println("MyCglibProxy.intercept -> 权限校验");
        }
        //默认调用父类的方法 也就是原来类的代码
        return methodProxy.invoke(targetObj, objects);
    }
}
