package work;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @program: rabbitmq_quickstart
 * @Description: 消息接收者1，通过ACK确认机制
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 15:16
 * @PackageName: simplest
 * @ClassName: Recer.java
 **/
public class Recer1 {
    static int i = 1;//统计吃掉羊肉串的数量

    public static void main(String[] args) throws Exception {
        //1. 获得连接
        Connection connection = ConnectionUtils.getConnection();
        //2. 获得通道
        Channel channel = connection.createChannel();
        //queueDeclare 该方法有双重作用，如果队列不存在，则创建；如果队列存在，则获取
        channel.queueDeclare("test_work_queue", false, false, false, null);
        channel.basicQos(1);
        //3. 从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【顾客1】吃掉：" + s + " ！总共吃【" + i++ + "】串！");
                //模拟网络延迟 吃掉1串花费0.2s
                try {
                    Thread.sleep(200);
                } catch (Exception e) {

                }
                //表示:手动确认
                //第二参数：是否同时确认多个消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //4. 监听队列
        //第二个参数：true 表示自动消息确认;false 手动确认消息
        channel.basicConsume("test_work_queue", false, consumer);
    }
}
