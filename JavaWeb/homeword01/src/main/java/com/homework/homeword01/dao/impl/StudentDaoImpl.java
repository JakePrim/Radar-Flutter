package com.homework.homeword01.dao.impl;

import com.homework.homeword01.dao.StudentDao;
import com.homework.homeword01.pojo.PageHelp;
import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.SClassService;
import com.homework.homeword01.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//班级名称、年级、班主任名称、班级口号 、班级人数
public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> findAll() {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        List<Student> students = null;
        try {
            //查询学生信息以及与之关联的班级信息
            students = qr.query("select * from t_student", new BeanListHandler<>(Student.class));
            //查询班级信息
            for (Student student : students) {
                int cid = student.getCid();
                SClassService classService = new SClassService();
                SClass sClass = classService.findById(cid);
                student.setsClass(sClass);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    /**
     * 实现分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageHelp<Student> findPage(int page, int pageSize) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        List<Student> students = null;
        Long total = 0L;
        try {
            //查询总数
            total = qr.query("select count(1) from t_student", new ScalarHandler<>());
            //1  1
            //2  5
            //3  10  page-1 * size

            //查询学生信息以及与之关联的班级信息
            students = qr.query("select * from t_student limit ?,?", new BeanListHandler<>(Student.class), page == 1 ? page : (page - 1) * pageSize, pageSize);
            //查询班级信息
            for (Student student : students) {
                int cid = student.getCid();
                SClassService classService = new SClassService();
                SClass sClass = classService.findById(cid);
                student.setsClass(sClass);
                System.out.println("student:" + student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new PageHelp<Student>(students, total.intValue(), page, pageSize);
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
        //设计到多个表的修改需要开启事物
        Connection connection = null;
        int update = 0;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            String sql = "insert into t_student values(null,?,?,?,?,?,?)";
            update = qr.update(connection, sql,
                    student.getName(),
                    student.getSex(),
                    student.getBrithday(),
                    student.getEmail(),
                    student.getDesc(),
                    student.getCid());
            //更新班级表中的学生数
            if (student.getCid() != 0) {
                SClassService service = new SClassService();
                service.updateNumAdd(connection, student.getCid());
            }
            connection.commit();
        } catch (SQLException throwables) {
            if (null != connection) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        } finally {
            DruidUtils.close(connection);
        }
        return update;
    }

    @Override
    public int delete(int id) {
        Connection connection = null;
        int update = 0;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            String sql = "delete from t_student where id = ?";
            //查询当前学生所在班级
            Student student = findById(id);
            //更新班级人数
            if (student.getCid() != 0) {
                SClassService service = new SClassService();
                service.updateNumSub(connection, student.getCid());
            }
            update = qr.update(connection, sql, id);
            connection.commit();
        } catch (SQLException throwables) {
            if (null != connection) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        } finally {
            DruidUtils.close(connection);
        }
        return update;
    }

    @Override
    public int update(Student student) {
        Connection connection = null;
        int update = 0;
        try {
            connection = DruidUtils.getConnection();
            connection.setAutoCommit(false);
            QueryRunner qr = new QueryRunner();
            //更新班级表中的学生数
            SClassService service = new SClassService();
            //1. 首先查询之前所在的班级 人数-1
            Student s = findById(student.getId());
            if (s.getCid() != 0 && s.getCid() != student.getCid()) {
                System.out.println("更新数据 存在之前的班级：" + s.getCid());
                service.updateNumSub(connection, s.getCid());
            }
            String sql = "update t_student set name=?,sex=?,brithday=?,email=?,`desc`=?,cid=? where id = ?";
            update = qr.update(connection, sql,
                    student.getName(),
                    student.getSex(),
                    student.getBrithday(),
                    student.getEmail(),
                    student.getDesc(),
                    student.getCid(),
                    student.getId());
            //2. 改变更改过后的班级 人数+1
            if (student.getCid() != 0 && s.getCid() != student.getCid()) {
                System.out.println("更新数据 修改班级：" + student.getCid());
                service.updateNumAdd(connection, student.getCid());
            }
            connection.commit();
        } catch (SQLException throwables) {
            if (null != connection) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        } finally {
            DruidUtils.close(connection);
        }
        return update;
    }
}
