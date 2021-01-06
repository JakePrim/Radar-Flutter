package com.jakeprim.dao.impl;

import com.jakeprim.dao.CourseDao;
import com.jakeprim.pojo.Course;
import com.jakeprim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 课程模块DAO层的实现类
 */
public class CourseDaoImpl implements CourseDao {

    @Override
    public List<Course> findCourseList() {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "SELECT id,course_name,price,sort_num,status FROM course where is_del = ?";
            List<Course> courseList = qr.query(sql, new BeanListHandler<>(Course.class), 0);
            return courseList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
