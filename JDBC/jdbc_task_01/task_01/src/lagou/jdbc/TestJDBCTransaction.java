package lagou.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestJDBCTransaction {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //1. 获取连接
            connection = JDBCUtils.getConnection();
            //2. 开启事务
            connection.setAutoCommit(false);//手动提交事务
            //3. 获取预处理对象 执行SQL
            //3.1 tom账户-500
            ps = connection.prepareStatement("update account set money = money-? where name=?");
            ps.setDouble(1, 500.0);
            ps.setString(2, "tom");
            ps.executeUpdate();
            System.out.println(1/0);
            //3.2 jake账户+500
            ps = connection.prepareStatement("update account set money = money+? where name=?");
            ps.setDouble(1, 500.0);
            ps.setString(2, "jake");
            ps.executeUpdate();
            //4. 提交/回滚事务
            connection.commit();
            System.out.println("转账成功");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            //事务回滚
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            //5. 关闭资源
            JDBCUtils.close(connection, ps);
        }
    }
}
