package com.example.factory;

import com.example.service.AccountService;
import com.example.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理工厂类
 */
@Component //对象存入IOC容器中
public class JdkProxyFactory {

    @Autowired //注入AccountService实例对象
    private AccountService accountService;

    @Autowired //注入TransactionManager实例对象
    private TransactionManager transactionManager;

    /**
     * 采用动态代理技术来生成目标类的代理对象
     * ClassLoader loader ： 类加载器，借助被代理对象获取类加载器
     * Class<?>[] interfaces：被代理类所需要实现的全部接口
     * InvocationHandler h ： 当代理对象代用代理接口的任意方法，都会执行invoke方法
     */
    public AccountService createAccountServiceProxy() {
        return (AccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                new Class[]{AccountService.class}, new InvocationHandler() {
                    //proxy:当前代理对象的引用
                    //method:被代理对象的原方法
                    //args：被调用目标方法的参数
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //拦截方法
                        try {
                            //如果是transfer则进行动态增强
                            if (method.getName().equals("transfer")) {
                                System.out.println("进行了前置增强");
                                transactionManager.beginTransaction();
                                method.invoke(accountService, args);//执行被代理对象的方法
                                System.out.println("进行了后置增强");
                                transactionManager.commit();
                            } else {
                                //否则不进行增强
                                method.invoke(accountService, args);//执行被代理对象的方法
                            }
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
