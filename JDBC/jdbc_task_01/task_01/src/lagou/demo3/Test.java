package lagou.demo3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        Connection connection = C3P0Utils.getConnection();

        String sql = "select * from employee where ename = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1,"李白");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("ename"));
            System.out.println(resultSet.getInt("eid"));
            System.out.println(resultSet.getInt("age"));
            System.out.println(resultSet.getString("sex"));
            System.out.println(resultSet.getDouble("salary"));
            System.out.println(resultSet.getDate("empdate"));
        }

        C3P0Utils.close(connection,ps,resultSet);
    }
}
