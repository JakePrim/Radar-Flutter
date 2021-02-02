package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.ConnectionUtils;

/**
 * @program: rabbitmq_quickstart
 * @Description: 消息生产者
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 16:05
 * @PackageName: work
 * @ClassName: Sender.java
 **/
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
//        channel.queueDeclare("test_work_queue", false, false, false, null);
        //声明路由,创建网红主播
        //第一个参数：路由名称
        //第二个参数：路由类型 一共有四种。
        //第三个参数：表示否是持久化路由
        //direct:根据路由键进行定向分发消息
        //topic:模糊匹配的定向分发
        channel.exchangeDeclare("text_exchange_topic", "topic", true);
        String msg = "订单下单";
        //推消息到路由器
        //第二个参数必填，路由键
        channel.basicPublish("text_exchange_topic", "order.down", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        System.out.println("[用户系统]：" + msg);
        //5. 释放资源
        channel.close();
        connection.close();

    }
}
