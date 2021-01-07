package lagou.testmetadata;

import lagou.druid.DruidUtils;
import org.junit.Test;

import java.sql.*;

public class TestMetaData {
    public static void main(String[] args) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        //获取数据库的元数据对象
        DatabaseMetaData metaData = connection.getMetaData();
        //获取数据库相关的元数据信息
        System.out.println("数据库的URL:" + metaData.getURL());
        //数据库的URL:jdbc:mysql://127.0.0.1:3306/db5?characterEncoding=UTF-8&rewriteBatchedStatements=true
        System.out.println("当前用户:" + metaData.getUserName());
        System.out.println("数据库产品名：" + metaData.getDatabaseProductName());
        System.out.println("数据库版本信息:" + metaData.getDatabaseProductVersion());
        System.out.println("获取数据库驱动程序名称:" + metaData.getDriverName());
        //判断当前数据库是否只允许只读
        System.out.println(metaData.isReadOnly() ? "数据库只读" : "数据库读写");
        connection.close();
    }

    //获取结果集中的元数据信息
    @Test
    public void testResultSet() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select * from employee";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();//获取到结果集

        //获取结果集元数据对象
        ResultSetMetaData metaData = ps.getMetaData();
        System.out.println("获取当前结果集，共有多少列:"+metaData.getColumnCount());
        System.out.println("获取结果集中，列的名称和类型:"+metaData.getColumnName(1)+":"+metaData.getColumnTypeName(1));
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            System.out.println("获取结果集中，列的名称和类型:"+metaData.getColumnName(i)+":"+metaData.getColumnTypeName(i));
        }
        DruidUtils.close(connection,ps);
    }
}
