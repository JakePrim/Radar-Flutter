package com.prim.aspectj.xml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 定义切面类 配置到配置文件中
 */
public class MyAspectXml {
    /**
     * 前置通知
     */
    public void before(JoinPoint joinPoint) {
        System.out.println("MyAspectXml.before XML方式 前置通知================" + joinPoint);
    }

    /**
     * 后置通知
     */
    public void afterReturning(Object result) {
        System.out.println("MyAspectXml.afterReturning XML方式 后置通知====================" + result);
    }

    /**
     * 环绕通知
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("MyAspectXml.around XML方式环绕通知之前================");
        Object proceed = joinPoint.proceed();
        System.out.println("MyAspectXml.around XML方式环绕通知之后================");
        return proceed;
    }

    public void afterThrowing(Throwable e) {
        System.out.println("MyAspectXml.afterThrowing XML方式异常抛出通知：" + e.getMessage());
    }

    public void after(){
        System.out.println("MyAspectXml.after XML方式最终通知");
    }
}
