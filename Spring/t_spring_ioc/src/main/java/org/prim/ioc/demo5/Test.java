package org.prim.ioc.demo5;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        CollectionBean collectionBean = (CollectionBean) applicationContext.getBean("collection");
        System.out.println("collectionBean = " + collectionBean);
    }
}
