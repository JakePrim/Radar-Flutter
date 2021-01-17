package com.example.factory;

import com.example.service.AccountService;
import com.example.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 采用Cglib动态代理
 */
@Component
public class CglibProxyFactory {
    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionManager transactionManager;

    public AccountService createProxyAccountService() {
        //参数1：目标类的字节码对象
        //参数2：动作类 当代理对象调用目标对象中的原方法时，会执行intercept方法
        return (AccountService) Enhancer.create(accountService.getClass(), new MethodInterceptor() {
            //o : 代表生成的代理对象
            //method：调用目标方法的引用
            //objects: 方法入参
            //methodProxy:代理方法
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                transactionManager.beginTransaction();
                try {
                    method.invoke(accountService, objects);
                    transactionManager.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionManager.rollback();
                } finally {
                    transactionManager.release();
                }
                return null;
            }
        });
    }
}
