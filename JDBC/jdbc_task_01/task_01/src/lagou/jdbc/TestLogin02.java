package lagou.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class TestLogin02 {
    //用户登录案例
    public static void main(String[] args) throws SQLException {
        //1. 获取连接
        Connection connection = JDBCUtils.getConnection();

        String sql = "select * from jdbc_user where username=? and password=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        //用户输入用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();
        //设置占位符的参数
        statement.setString(1,username);
        statement.setString(2,password);
        //执行SQL
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

    }
}
