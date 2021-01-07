package org.prim.ioc.demo3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        //如下即使不写下面的方式，applicationContext也会默认实例化Bean1
//        Bean1 bean1 = (Bean1) applicationContext.getBean("bean1");

        //静态工厂 可以看出Bean2Factory并没有实例化，只是调用了静态方法
//        Bean2 bean2 = (Bean2) applicationContext.getBean("bean2");

        //实例化工厂 首先实例化工厂 会调用构造方法 然后调用方法
//        Bean3 bean3 = (Bean3) applicationContext.getBean("bean3");

//        Persion persion = (Persion) applicationContext.getBean("person");
//        Persion persion2 = (Persion) applicationContext.getBean("person");
//        //默认返回都是一个实例 单例的
//        //scope : prototype 每次都返回一个新的实例
//        System.out.println("persion = " + persion + " persion2 = " + persion2);

//        Man man = (Man) applicationContext.getBean("man");
////        man.run();
////        applicationContext.close();

        UserDao userDao = (UserDao) applicationContext.getBean("userDao");
        userDao.findAll();
        userDao.save();
        userDao.update();
        userDao.delete();
    }
}
