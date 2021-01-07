package lagou.dbutils;

import lagou.druid.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * QueryRunner 对象 完成增删改
 * update(Connection,sql,Object... param)
 */
public class DBUtilsDemo02 {
    @Test
    public void testInsert() throws SQLException {
        //1. 创建QueryRunner 手动模式
        QueryRunner qr = new QueryRunner();
        //2. 编写SQL
        String sql = "insert into employee values(?,?,?,?,?,?)";
        //3. 设置占位符的参数
        Object[] param = {null, "张百万", 28, "男", 10000, "1990/1/1"};
        //设置对应的连接池
        Connection connection = DruidUtils.getConnection();
        qr.update(connection, sql, param);
        //释放资源
        DbUtils.closeQuietly(connection);
    }

    //修改操作 修改姓名为张百万的员工的工资为
    @Test
    public void testUpdate() {
        //自动模式 传入连接池对象
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        //编写SQL
        String sql = "update employee set salary = ? where ename=?";
        //设置占位符参数
        Object[] param = {5000, "张百万"};
        //执行修改操作
        try {
            //自动模式不需要传入 connection对象 自动管理连接池和关闭操作
            qr.update(sql, param);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //删除操作
    @Test
    public void testDelete() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "delete from employee where eid=?";
        qr.update(sql,1);
    }
}
