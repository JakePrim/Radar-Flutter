package com.prim.aop;

import com.prim.UserDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopImpl implements InvocationHandler {

    private Object proxyObj;

    private Object targetObj;

    public AopImpl(Object targetObj) {
        this.targetObj = targetObj;
    }

    /**
     * 创建一个代理对象
     * @return
     */
    public Object createProxy() {
        proxyObj = Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), targetObj.getClass().getInterfaces(), this);
        return proxyObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("save".equals(method.getName())) {
            System.out.println("权限校验.....");
            return method.invoke(targetObj, args);//做完权限校验后在调用目标对象的方法
        }
        return method.invoke(targetObj, args);//直接调用目标对象的方法
    }
}
