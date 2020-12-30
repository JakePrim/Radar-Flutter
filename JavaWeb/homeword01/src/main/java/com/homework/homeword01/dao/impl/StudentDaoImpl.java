package com.homework.homeword01.dao.impl;

import com.homework.homeword01.dao.StudentDao;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;
//班级名称、年级、班主任名称、班级口号 、班级人数
public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> findAll() {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        List<Student> students = null;
        try {
            students = qr.query("select * from t_student", new BeanListHandler<>(Student.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    @Override
    public Student findById(int id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        Student student = null;
        try {
            student = qr.query("select * from t_student where id=?", new BeanHandler<>(Student.class), id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return student;
    }

    @Override
    public int add(Student student) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "insert into t_student values(null,?,?,?,?,?)";
        int update = 0;
        try {
            update = qr.update(sql,
                    student.getName(),
                    student.getSex(),
                    student.getBrithday(),
                    student.getEmail(),
                    student.getDesc());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update;
    }

    @Override
    public int delete(int id) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "delete from t_student where id = ?";
        int update = 0;
        try {
            update = qr.update(sql, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update;
    }

    @Override
    public int update(Student student) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "update t_student set name=?,sex=?,brithday=?,email=?,`desc`=? where id = ?";
        int update = 0;
        try {
            update = qr.update(sql,
                    student.getName(),
                    student.getSex(),
                    student.getBrithday(),
                    student.getEmail(),
                    student.getDesc(),
                    student.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return update;
    }
}
