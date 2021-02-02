package simplest;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.ConnectionUtils;

/**
 * @program: rabbitmq_quickstart
 * @Description: 消息生产者
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 15:04
 * @PackageName: simplest
 * @ClassName: Sender.java
 **/
public class Sender {
    public static void main(String[] args) throws Exception {
        String msg = "A：Hello Rabbit MQ";

        //1. 获得连接
        Connection connection = ConnectionUtils.getConnection();
        //2. 在连接中创建通道
        Channel channel = connection.createChannel();
        //3. 创建消息队列
        /**
         * 参数1：队列名称
         * 参数2：队列中的数据是否持久化
         * 参数3：是否排外 是否支持扩展，当前队列只能自己用，不能给别人用
         * 参数4：是否自动删除(当队列的连接数为0时，队列会销毁，不管队列中是否还保存数据)
         * 参数5：队列参数，没有参数传null
         */
        channel.queueDeclare("queue1", false, false, false, null);
        //4. 向指定的队列发送消息
        /**
         * 参数1：交换机名称，当前是简单模式-点对点模式 没有交换机，所以名称为""
         * 参数2：目标队列的名称：queue1
         * 参数3：设置消息的属性，没有属性则为null
         * 参数4：消息内容 直接接收byte[]
         */
        channel.basicPublish("", "queue1", null, msg.getBytes());
        System.out.println("已发送：" + msg);
        //5. 释放资源
        channel.close();
        connection.close();
    }
}
