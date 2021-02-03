package test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-rabbitmq-producer
 * @Description: 死信测试
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 22:30
 * @PackageName: test
 * @ClassName: Sender.java
 **/
public class SenderDLX {
    public static void main(String[] args) {
        //1. 创建spring容器
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producer-dlx.xml");
        //2. 从容器中获得Rabbit模板对象
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        //传递路由键，将消息传递到配置的test_ttl_queue队列
        rabbitTemplate.convertAndSend("dlx_ttl", "超时，关闭订单".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max", "测试长度2".getBytes());
//        rabbitTemplate.convertAndSend("dlx_max", "测试长度3".getBytes());
        System.out.println("[消息已发出]");
        applicationContext.close();
    }
}
