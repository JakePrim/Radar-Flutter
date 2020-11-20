package com.prim.pointcut.autoproxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author prim
 * 环绕通知
 */
public class MyAroundAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //在执行之前进行一下操作
        System.out.println("MyAroundAdvice.invoke 环绕前增强");

        //执行目标方法
        Object proceed = methodInvocation.proceed();

        System.out.println("MyAroundAdvice.invoke 环绕后增强");
        return null;
    }
}
