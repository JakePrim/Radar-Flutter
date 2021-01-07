package org.prim.ioc.demo4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author prim
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");

//        User user = (User) applicationContext.getBean("user");
//        System.out.println("user = " + user);

//        Person person = (Person) applicationContext.getBean("person");
//        System.out.println("person = " + person);

        Produce produce = (Produce) applicationContext.getBean("product");
        System.out.println("produce = " + produce);
    }
}
