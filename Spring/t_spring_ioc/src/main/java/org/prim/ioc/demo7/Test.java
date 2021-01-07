package org.prim.ioc.demo7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
//        Bean1 bean1 = (Bean1) applicationContext.getBean("bean1");
//        Bean1 bean2 = (Bean1) applicationContext.getBean("bean1");
//        bean1.say();
//        System.out.println("bean2 = " + bean2 + " bean1=" + bean1);
//        applicationContext.close();

        ProductService productService = (ProductService) applicationContext.getBean("productService");
        productService.save();
    }
}
