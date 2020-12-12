package lagou.demo04;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Druid 工具类
 */
public class DruidUtils {
    //1. 定义成员变量
    public static DataSource dataSource;

    //2. 静态代码块
    static {
        try {
            //3. 创建属性集对象
            Properties p = new Properties();
            //4. 加载配置文件 Druid 连接池不能够主动加载配置文件需要手动指定
            InputStream inputStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            //Properties的load方法从字节流中读取配置信息
            p.load(inputStream);
            //5. 通过Druid的工厂类来获取连接池对象
            dataSource = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //2. 获取连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //获取数据库连接池对象的方法
    public static DataSource getDataSource() {
        return dataSource;
    }

    //5. 释放资源
    public static void close(Connection connection, Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
