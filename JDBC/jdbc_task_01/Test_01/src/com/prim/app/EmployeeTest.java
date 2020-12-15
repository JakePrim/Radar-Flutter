package com.prim.app;

import com.prim.dao.EmployeeDao;
import com.prim.entity.Dept;
import com.prim.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeTest {
    public static void main(String[] args) throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        System.out.println("查询所有的员工信息 (不包含没有部门的员工):");
        List<Employee> employeeList = employeeDao.findAllEmployeeByNotDept();
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }

        System.out.println("查询每个员工的 姓名, 薪资 和 所属部门名称:");
        List<Employee> employees = employeeDao.findAllEmployee();
        for (Employee employee : employees) {
            Dept dept = employee.getDept();
            System.out.println(employee.getName() + ":" + employee.getSalary() + ":" + (dept != null ? dept.getDeptname() : ""));
        }
    }
}
