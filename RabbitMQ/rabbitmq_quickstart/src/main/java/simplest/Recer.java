package simplest;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @program: rabbitmq_quickstart
 * @Description: 消息接受者
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 15:16
 * @PackageName: simplest
 * @ClassName: Recer.java
 **/
public class Recer {
    public static void main(String[] args) throws Exception {
        //1. 获得连接
        Connection connection = ConnectionUtils.getConnection();
        //2. 获得通道
        Channel channel = connection.createChannel();
        //3. 从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                java.lang.String s = new java.lang.String(body);
                System.out.println("接收=" + s);
            }
        };
        //4. 监听队列
        //第二个参数：true 表示自动消息确认
        channel.basicConsume("queue1",true, consumer);
    }
}
