package work;

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
        channel.queueDeclare("test_work_queue", false, false, false, null);
        //生产100个肉串
        for (int i = 1; i <= 100; i++) {
            String msg = "羊肉串 --> " + i;
            channel.basicPublish("", "test_work_queue", null, msg.getBytes());
            System.out.println("新鲜出炉：" + msg);
        }
        //5. 释放资源
        channel.close();
        connection.close();

    }
}
