package lagou.dbcp;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DBCP 连接池工具类
 */
public class DBCPUtils {
    //1. 定义常量 保存数据库连接的相关信息
    public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/db5?characterEncoding=UTF-8";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "123456";

    //2. 创建连接池对象
    public static BasicDataSource dataSource = new BasicDataSource();

    //3. 使用静态代码块进行配置
    static {
        dataSource.setDriverClassName(DRIVER_NAME);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
    }

    //4. 获取连接的方法
    public static Connection getConnection() throws SQLException {
        //从连接池中获取
        Connection connection = dataSource.getConnection();
        return connection;
    }

    //5. 释放资源
    public static void close(Connection connection, Statement statement) {
        if (null != statement){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != connection){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (null != resultSet){
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != statement){
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != connection){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
