package com.example.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 通知类 注解方式
 */
@Component
@Aspect //升级为切面类：配置切入点和通知的关系
public class AccountAdvice {

    //抽取切点表达式
    @Pointcut("execution(* com.example.impl.AccountServiceImpl.*(..))")
    public void accountPointcut(){

    }

    @Before("AccountAdvice.accountPointcut()")//表达式
    public void before() {
        System.out.println("前置通知，执行。。。。");
    }

    @AfterReturning("AccountAdvice.accountPointcut()")
    public void after() {
        System.out.println("后置通知，执行。。。。。");
    }

    @AfterThrowing("AccountAdvice.accountPointcut()")
    public void afterThowing() {
        System.out.println("异常通知，执行");
    }

    @After("AccountAdvice.accountPointcut()")
    public void finals() {
        System.out.println("最终通知，执行");
    }

    /**
     * @param joinPoint 切入点
     * @return
     */
    @Around("execution(* com.example.impl.AccountServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        System.out.println("环绕通知");
        Object result = null;
        try {
            System.out.println("前置通知，，");
            result = joinPoint.proceed();//执行切点方法
            System.out.println("后置通知，，");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("异常通知，，");
        } finally {
            System.out.println("最终通知，，");
        }
        return result;
    }
}
