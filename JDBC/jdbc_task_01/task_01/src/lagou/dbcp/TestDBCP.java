package lagou.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDBCP {
    public static void main(String[] args) throws SQLException {
        //1. 从DBCP 连接池中拿到连接
        Connection connection = DBCPUtils.getConnection();
        //2. 获取statement
        Statement statement = connection.createStatement();
        //3. 查询操作
        String sql = "select ename from employee";
        ResultSet resultSet = statement.executeQuery(sql);
        //4. 处理结果集
        while (resultSet.next()){
            String ename = resultSet.getString("ename");
            System.out.println("ename:"+ename);
        }
        //5. 释放资源
        DBCPUtils.close(connection,statement,resultSet);
    }
}
