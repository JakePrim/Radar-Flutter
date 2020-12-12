package lagou.demo01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TestPS {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtils.getConnection();

        Statement statement = connection.createStatement();

        //向数据库插入两条数据
        //statement 每次执行都需要编译 如果SQL语句特别多 那么性能会很差
        statement.executeUpdate("insert into jdbc_user values(null,'张三','123','2020/1/1')");
        statement.executeUpdate("insert into jdbc_user values(null,'张三','123','2020/1/1')");

        //获取预处理对象
        //预处理对象会将SQL发送给数据进行一个预编译，然后将预编译的SQL保存出来 这样只需要编译一次
        PreparedStatement ps = connection.prepareStatement("insert into jdbc_user values(?,?,?,?)");

        ps.setObject(1,null);
        ps.setString(2,"项王");
        ps.setString(3,"123");
        ps.setString(4,"2020/2/2");

        ps.executeUpdate();

        //插入第二条数据
        ps.setObject(1,null);
        ps.setString(2,"项王2");
        ps.setString(3,"123");
        ps.setString(4,"2020/2/22");

        ps.executeUpdate();

        //释放资源
        JDBCUtils.close(connection,statement);
    }
}
