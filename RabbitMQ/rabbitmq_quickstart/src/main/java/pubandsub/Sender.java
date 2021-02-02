package pubandsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
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
        //fanout类型：不处理路由键(只需要将队列绑定到路由上，发送到路由的消息就都会被转发到与该路由绑定的所有队列上)
        channel.exchangeDeclare("text_exchange_fanout", "fanout");
        String msg = "hello everyone";
        //向绑定路由、网红主播的人发送消息
        channel.basicPublish("text_exchange_fanout", "", null, msg.getBytes());
        System.out.println("生产者：" + msg);
        //5. 释放资源
        channel.close();
        connection.close();

    }
}
