package listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.adapter.AbstractAdaptableMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: spring-rabbit-consumer
 * @Description: 消费者队列
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 22:36
 * @PackageName: listener
 * @ClassName: ConsumerListener.java
 * Spring容器接收到消息后用于处理消息的基类,可以拿到channel信道
 **/
@Component
public class ConsumerListener extends AbstractAdaptableMessageListener {
    //jackson提供序列化和反序列化中使用最多的类，转换json的
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        // 将Message对象转换成json
        try {
            byte[] body = message.getBody();
            String s = new String(body);
            System.out.println(s);
//            JsonNode jsonNode = MAPPER.readTree(message.getBody());
//            String name = jsonNode.get("name").asText();
//            String email = jsonNode.get("email").asText();
//            System.out.println("从队列中获取：" + name + "的邮箱是：" + email);
            //手动确认消息
            //参数1：RabbitMQ向该channel投递的这条消息的唯一标识id，此id是一个单调递增的正整数
            //参数2：为了减少网络流量，手动确认可以被批量处理。当为true时，则可以一次性确认小于等于messageId值的所有消息
            //加入messageId=8 则可以确认<=8的所有消息
            long messageId = message.getMessageProperties().getDeliveryTag();
//            System.out.println("messageId:" + messageId);
            channel.basicAck(messageId, true);
//            Thread.sleep(3000);
//            System.out.println("休息三秒后再继续接收消息");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
