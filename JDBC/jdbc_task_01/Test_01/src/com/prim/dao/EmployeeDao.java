package com.prim.dao;

import com.prim.entity.Dept;
import com.prim.entity.Employee;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class EmployeeDao {
    public List<Employee> findAllEmployeeByNotDept() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee where did is not null";
        List<Employee> employees = qr.query(sql, new BeanListHandler<>(Employee.class));
        return employees;
    }

    public List<Employee> findAllEmployee() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from employee";
        List<Employee> employees = qr.query(sql, new BeanListHandler<>(Employee.class));
        for (Employee employee : employees) {
            Dept dept = findDept(employee.getDid());
            employee.setDept(dept);
        }
        return employees;
    }

    public Dept findDept(int did) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from dept where id=?";
        Dept query = qr.query(sql, new BeanHandler<>(Dept.class), did);
        return query;
    }
}
