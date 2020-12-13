package lagou.jdbc;

import java.sql.*;

public class JDBCDemo02 {
    public static void main(String[] args) {
        //1. 注册驱动
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2. 获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db5", "root", "123456");
            //3. 获取语句执行平台对象
            statement = connection.createStatement();
            //4. 执行查询操作
            String sql = "select * from jdbc_user;";
            //resultset 是结果集对象 需要进行遍历取出每一条记录
            resultSet = statement.executeQuery(sql);
            //判断是否有下一条数据
            while (resultSet.next()) {
                //获取id
                int id = resultSet.getInt("id");// 推荐 通过列名的方式获取
                String username = resultSet.getString("username");//获取名字
                String password = resultSet.getString("password");//获取密码
                Date date = resultSet.getDate("brithday");
                System.out.println(id + ":" + username + ":" + password + ":" + date);
                //            resultSet.getInt(1);//不推荐 通过列号的方式获取  1表示某行的第一列
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5. 关闭流操作
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }
}
