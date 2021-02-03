package transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import utils.ConnectionUtils;

import java.io.IOException;

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
        channel.exchangeDeclare("text_transaction_topic", "topic");
        try {
            //开启事务
            channel.txSelect();
            //推消息到路由器
            //第二个参数必填，路由键
            channel.basicPublish("text_transaction_topic", "order.down", null, "商品1-降价".getBytes());
            int i = 1 / 0;//出现了异常会导致：成功的成功，失败的失败，加入事务要么全部成功，要么全部失败
            channel.basicPublish("text_transaction_topic", "order.down", null, "商品2-降价".getBytes());
            //提交事务
            channel.txCommit();
            System.out.println("[消息已发送]");
        } catch (IOException e) {
            System.out.println("消息全部撤销");
            //回滚事务
            channel.txRollback();
        } finally {
            //5. 释放资源
            channel.close();
            connection.close();
        }
    }
}
