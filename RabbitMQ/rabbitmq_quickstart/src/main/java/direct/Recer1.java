package pubandsub;

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
public class Recer1 {
    public static void main(String[] args) throws Exception {
        //1. 获得连接
        Connection connection = ConnectionUtils.getConnection();
        //2. 获得通道
        Channel channel = connection.createChannel();
        //queueDeclare 该方法有双重作用，如果队列不存在，则创建；如果队列存在，则获取
        //声明队列
        channel.queueDeclare("test_exchange_fanout_queue_1", false, false, false, null);
        //关注网红，绑定路由
        /**
         * 参数1：队列名
         * 参数2：路由名
         */
        channel.queueBind("test_exchange_fanout_queue_1","text_exchange_fanout","");
        //3. 从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 交付处理（收件人信息，包裹上的快递标签，协议的配置，消息）
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("【消费者1】：" + s);
            }
        };
        //4. 监听队列
        //第二个参数：true 表示自动消息确认;false 手动确认消息
        channel.basicConsume("test_exchange_fanout_queue_1", true, consumer);
    }
}
