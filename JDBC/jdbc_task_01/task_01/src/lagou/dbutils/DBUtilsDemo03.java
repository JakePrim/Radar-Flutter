package lagou.dbutils;

import lagou.druid.DruidUtils;
import lagou.entity.Employee;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DBUtilsDemo03 {
    /**
     * ArrayHandler 将结果集的第一条数据封装到数组中
     */
    @Test
    public void testFindById() throws SQLException {
        //1. 创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where eid=?";
        Object[] query = qr.query(sql, new ArrayHandler(), 5);
        //获取数据
        System.out.println(Arrays.toString(query));
    }

    /**
     * 查询所有的数据 封装到list集合中
     * ArrayListHandler 可以将每条数据先封装到数组中，再将数组封装到集合中
     */
    @Test
    public void findAll() throws SQLException {
        //1. 创建QueryRunner
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee";
        List<Object[]> query = qr.query(sql, new ArrayListHandler());
        for (Object[] objects : query) {
            System.out.println(Arrays.toString(objects));
        }
    }

    /**
     * 封装到指定的javabean中
     * BeanHandler
     */
    @Test
    public void findJavaBean() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where eid=?";
        Employee query = qr.query(sql, new BeanHandler<>(Employee.class), 5);
        System.out.println(query.toString());
    }

    /**
     * 封装Javabean封装到list集合中
     * BeanListHandler 将结果集的每一条和数据封装到javabean中 再将Javabean放到list集合中
     */
    @Test
    public void findSalaryListBean() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where salary > ?";
        List<Employee> query = qr.query(sql, new BeanListHandler<Employee>(Employee.class), 3000);
        for (Employee employee : query) {
            System.out.println(employee);
        }
    }

    /**
     * 将查询的结果集封装到map集合中
     * MapHandler 将结果集的第一条记录封装到Map<String,Object>中 key对应的是列名   value对应的是列值
     */
    @Test
    public void findMapHandler() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where ename=?";
        Map<String, Object> query = qr.query(sql, new MapHandler(), "张百万");
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }

    /**
     * ScalarHandler 用于封装单个的数据
     */
    @Test
    public void testCount() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select sum(salary) from employee";
        Double salarySum = qr.query(sql, new ScalarHandler<Double>());
        System.out.println(salarySum);
    }
}
