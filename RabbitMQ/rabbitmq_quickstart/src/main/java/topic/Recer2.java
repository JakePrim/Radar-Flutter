package topic;

import com.rabbitmq.client.*;
import utils.ConnectionUtils;

import java.io.IOException;

/**
 * @program: rabbitmq_quickstart
 * @Description: 消息接收者1
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 15:16
 * @PackageName: simplest
 * @ClassName: Recer.java
 **/
public class Recer2 {
    public static void main(String[] args) throws Exception {
        //1. 获得连接
        Connection connection = ConnectionUtils.getConnection();
        //2. 获得通道
        Channel channel = connection.createChannel();
        //queueDeclare 该方法有双重作用，如果队列不存在，则创建；如果队列存在，则获取
        //声明队列
        channel.queueDeclare("test_exchange_topic_queue_2", true, false, false, null);
        /**
         * 绑定商品和订单相关的消息
         * 参数1：队列名
         * 参数2：路由名
         * 参数3：路由键
         */
        channel.queueBind("test_exchange_topic_queue_2", "text_exchange_topic", "product.#");
        channel.queueBind("test_exchange_topic_queue_2", "text_exchange_topic", "order.#");
        //3. 从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者2】：" + s);
            }
        };
        //4. 监听队列
        //第二个参数：true 表示自动消息确认;false 手动确认消息
        channel.basicConsume("test_exchange_topic_queue_2", true, consumer);
    }
}
