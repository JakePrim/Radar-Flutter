package com.prim.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 切面类
 * 注意将切面定义到applicationContext
 *
 * @author prim
 */
@Aspect
public class MyAspectAnno {
    /**
     * 前置通知
     * value 定义切入点:在save的时候进行校验
     * JoinPoint 获得切点信息
     */
    @Before(value = "myPointcut1()||myPointcut2()")
    public void before(JoinPoint joinPoint) {
        System.out.println("MyAspectAnno.before 权限校验 ===================" + joinPoint);
    }

    /**
     * 后置通知
     * returning 获得切入方法的返回值
     */
    @AfterReturning(value = "myPointcut2()", returning = "result")
    public void afterReturning(Object result) {
        System.out.println("MyAspectAnno.afterReturning 后置通知 ===================" + result);
    }

    @Around(value = "myPointcut3()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("MyAspectAnno.around 环绕前通知=======================");
        //执行目标方法 如果不调用目标方法就会不执行了
        Object proceed = joinPoint.proceed();
        System.out.println("MyAspectAnno.around 环绕后通知======================");
        return proceed;
    }

    @AfterThrowing(value = "myPointcut4()", throwing = "e")
    public void afterThrowing(Throwable e) {
        System.out.println("MyAspectAnno.afterThrowing 异常抛出通知" + e.getMessage());
    }

    @After(value = "myPointcut5()")
    public void after() {
        System.out.println("MyAspectAnno.after 最终通知===============================");
    }

    /**
     * 定义一个切点 方便维护
     */
    @Pointcut(value = "execution(* com.prim.aspectj.ProductDao.save(..))")
    private void myPointcut1() {
    }

    @Pointcut(value = "execution(* com.prim.aspectj.ProductDao.update(..))")
    private void myPointcut2() {
    }

    @Pointcut(value = "execution(* com.prim.aspectj.ProductDao.delete(..))")
    private void myPointcut3() {
    }

    @Pointcut(value = "execution(* com.prim.aspectj.ProductDao.findAll(..))")
    private void myPointcut4() {
    }

    @Pointcut(value = "execution(* com.prim.aspectj.ProductDao.findOne(..))")
    private void myPointcut5() {
    }
}
