package com.example.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器工具类：开启事务、提交事务、回滚事务、提交资源
 * 通知类
 */
@Component
@Aspect //升级为切面类
public class TransactionManager {

    //注入 连接工具类
    @Autowired
    private ConnectionUtils connectionUtils;

    //切点表达式抽取
    @Pointcut("execution(* com.example.service.impl.AccountServiceImpl.*(..))")
    public void transactionPointcut() {

    }

    @Around("TransactionManager.transactionPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            beginTransaction();
            result = joinPoint.proceed();
            commit();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            rollback();
        } finally {
            release();
        }
        return result;
    }

    /**
     * 开启手动提交事务
     */
//    @Before("TransactionManager.transactionPointcut()")
    public void beginTransaction() {
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.setAutoCommit(false);//手动提交事务
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
//    @AfterReturning("TransactionManager.transactionPointcut()")
    public void commit() {
        Connection connection = connectionUtils.getThreadConnection();
        try {
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
//    @AfterThrowing("TransactionManager.transactionPointcut()")
    public void rollback() {
        Connection threadConnection = connectionUtils.getThreadConnection();
        try {
            threadConnection.rollback();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 释放资源
     */
//    @After("TransactionManager.transactionPointcut()")
    public void release() {
        try {
            //1 将手动事务改回自动提交事务
            Connection connection = connectionUtils.getThreadConnection();
            connection.setAutoCommit(true);
            //2 将连接归还
            connectionUtils.getThreadConnection().close();
            //3 解除线程绑定
            connectionUtils.removeThreadLocal();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
