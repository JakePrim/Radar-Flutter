package test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-rabbitmq-producer
 * @Description: 消息生产者
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 22:30
 * @PackageName: test
 * @ClassName: Sender.java
 **/
public class Sender2 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producer.xml");
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        //创建消息的配置对象
        MessageProperties properties = new MessageProperties();
        properties.setExpiration("3000");//设置过期时间三秒
        //创建消息
        Message message = new Message("测试过期时间".getBytes(), properties);
        rabbitTemplate.convertAndSend("msg.user", message);
        System.out.println("[消息已发出]");
        applicationContext.close();
    }
}
