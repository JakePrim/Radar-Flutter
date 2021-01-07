package lagou.testbatch;

import lagou.druid.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchInsert {
    public static void main(String[] args) throws SQLException {
        // 使用批处理向表中添加10000条数据
        Connection connection = DruidUtils.getConnection();
        PreparedStatement ps = connection.prepareStatement("insert into testBatch(cname) values(?)");
        // 执行批量插入操作
        for (int i = 0; i < 10000; i++) {
            ps.setString(1, "小强:" + i);
            //将SQL 添加到批处理列表里面
            ps.addBatch();
        }
        long start = System.currentTimeMillis();
        // 执行批处理操作
        ps.executeBatch();
        long end = System.currentTimeMillis();
        System.out.println("插入10000条数据需要使用:" + (end - start));
        //为什么插入了1000条 而不是10000条呢？
        DruidUtils.close(connection, ps);
    }
}
