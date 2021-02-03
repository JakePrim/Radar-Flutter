package test;

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
public class Sender {
    public static void main(String[] args) {
        //1. 创建spring容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-rabbitmq-producer.xml");
        //2. 从容器中获得Rabbit模板对象
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        //3. 发送消息
        Map<String, String> map = new HashMap<>();
        map.put("name", "吕布");
        map.put("email", "666@qq.com");
        //将消息发送一个不存在的路由 测试消息发送失败
//        for (int i = 0; i < 10; i++) {
        rabbitTemplate.convertAndSend("msg.user", map);
//        }
        System.out.println("[消息已发出]");
        applicationContext.close();
    }
}
