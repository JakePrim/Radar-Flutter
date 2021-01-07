package lagou.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDQL {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from jdbc_user where username='刘能'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            System.out.println(id+":"+username);
        }
        JDBCUtils.close(connection,statement,resultSet);
    }
}
