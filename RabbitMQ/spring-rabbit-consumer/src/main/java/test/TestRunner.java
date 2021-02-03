package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @program: spring-rabbit-consumer
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 23:17
 * @PackageName: test
 * @ClassName: TestRunner.java
 **/
public class TestRunner {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring/spring-rabbitmq-consumer.xml");
        System.in.read();//程序一直运行
    }
}
