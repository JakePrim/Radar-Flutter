package lagou.demo04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDruid {
    public static void main(String[] args) throws SQLException {
        Connection connection = DruidUtils.getConnection();

        String sql = "select * from employee where salary between 3000 and 5000";
        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("ename"));
            System.out.println(resultSet.getInt("eid"));
            System.out.println(resultSet.getInt("age"));
            System.out.println(resultSet.getString("sex"));
            System.out.println(resultSet.getDouble("salary"));
            System.out.println(resultSet.getDate("empdate"));
        }

        DruidUtils.close(connection, ps, resultSet);
    }
}
