package lagou.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动 加载Driver类 注册驱动 这句话可以省略
        Class.forName("com.mysql.jdbc.Driver");

        //2. 获取连接 user：用户名 password：密码 url：数据库的地址
        //jdbc:mysql://localhost:3306/db5
        //jdbc:mysql:  -> 协议
        //localhost:3306 -> 主机IP 和端口号
        ///db5 -> 数据库
        //? 参数名=参数值
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db5?characterEncoding=UTF-8", "root", "123456");
        System.out.println(connection);//com.mysql.jdbc.JDBC4Connection@2f333739

        //3. 获取语句执行平台对象 Statement对象
        Statement statement = connection.createStatement();

        //3.1 通过executeUpdate方法执行增删改操作
        String sql = "create table test(id int,name varchar(20),age int);";
        //返回值表示受影响的行数
        int i = statement.executeUpdate(sql);

        System.out.println("i:"+i);

        //4. 关闭流 后开先关
        statement.close();
        connection.close();
    }
}
