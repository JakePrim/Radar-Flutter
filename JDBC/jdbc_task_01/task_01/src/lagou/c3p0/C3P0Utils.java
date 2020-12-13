package lagou.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * C3P0 连接池工具类
 */
public class C3P0Utils {
    //1. 创建连接池对象 C3P0对DataSource接口的实现类
    //使用空参的形式是配置文件中的默认配置：default-config
//    public static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    //使用指定的配置：named-config name="mysql"  参数传递name即可
    public static ComboPooledDataSource dataSource = new ComboPooledDataSource("mysql");

    //2. 获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
