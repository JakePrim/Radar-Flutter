package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @program: rabbitmq_quickstart
 * @Description: 专门与RabbitMQ获得连接
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-02 14:19
 * @PackageName: utils
 * @ClassName: ConnectionUtils.java
 **/
public class ConnectionUtils {
    public static Connection getConnection() throws Exception {
        //1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2. 在工厂对象中设置MQ的连接信息 - ip post vhost username password
        factory.setHost("172.16.150.130");
        factory.setPort(5672);
        factory.setVirtualHost("/edu");//在第一步创建的虚拟主机
        factory.setUsername("prim"); //在安装时创建的账号和密码
        factory.setPassword("123456");
        //3. 通过工厂获得与MQ的连接
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = getConnection();
        System.out.println("connection:" + connection);
        //关闭链接
        connection.close();
    }
}
