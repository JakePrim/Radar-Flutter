package org.prim.ioc;

import org.prim.ioc.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Arrays;

/**
 * @author prim
 */
public class Test {
    public static void main(String[] args) {
//        UserService userService = new UserServiceImpl();
//        userService.sayHelloWorld();
        //初始化工厂
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //ClassPathXmlApplicationContext 加载类路径下的配置文件


        //FileSystemXmlApplicationContext 加载磁盘某个目录下的配置文件
//        ApplicationContext applicationContext =
//                new FileSystemXmlApplicationContext("Users/prim/Desktop/applicationContext.xml");
        //通过工厂来获的类的实例
        Food food = (Food) applicationContext.getBean("food");
        System.out.println("args = " + food.toString());
    }
}
