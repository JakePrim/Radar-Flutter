package lagou.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestLogin {
    //用户登录案例
    public static void main(String[] args) throws SQLException {
        //1. 获取连接
        Connection connection = JDBCUtils.getConnection();
        Statement statement = connection.createStatement();
        //用户输入用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();

        String sql = "select * from jdbc_user where username='"+username+"' and password='"+password+"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

    }
}
