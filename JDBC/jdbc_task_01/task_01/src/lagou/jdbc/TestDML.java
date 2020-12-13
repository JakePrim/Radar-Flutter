package lagou.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc DML操作
 */
public class TestDML {

    @Test
    public void testInsert() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        String sql = "insert into jdbc_user values(null,'张百万','123','1992/10/21')";
        int i = statement.executeUpdate(sql);
        System.out.println("影响行数:"+i);
        JDBCUtils.close(connection,statement);
    }

    @Test
    public void testUpdate() throws SQLException {
        //根据id修改用户名
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        String sql = "update jdbc_user set username='刘能' where id=5";
        int i = statement.executeUpdate(sql);
        System.out.println("影响行数:"+i);
        JDBCUtils.close(connection,statement);
    }

    @Test
    public void testDelete() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        String sql = "delete from jdbc_user where id=6";
        int i = statement.executeUpdate(sql);
        System.out.println("影响行数:"+i);
        JDBCUtils.close(connection,statement);
    }
}
