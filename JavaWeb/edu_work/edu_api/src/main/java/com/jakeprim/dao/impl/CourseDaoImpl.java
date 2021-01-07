package com.jakeprim.dao.impl;

import com.jakeprim.dao.CourseDao;
import com.jakeprim.pojo.Course;
import com.jakeprim.utils.DateUtils;
import com.jakeprim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            //没有条件的情况下加上1=1 -小技巧
            StringBuilder sb = new StringBuilder("SELECT id,course_name,price,sort_num,status FROM course where 1=1 AND is_del = ?");
            //1. 创建参数列表
            List<Object> params = new ArrayList<>();
            params.add(0);
            if (courseName != null && !"".equals(courseName)) {
                sb.append(" AND course_name LIKE ?");
                //1.2 添加参数
                courseName = "%" + courseName + "%";
                params.add(courseName);
            }
            if (status != null && !"".equals(status)) {
                sb.append(" AND status=?");
                //1.3 添加参数
                params.add(Integer.parseInt(status));
            }
            List<Course> courseList = qr.query(sb.toString(), new BeanListHandler<>(Course.class), params.toArray());
            return courseList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveCourseSalesInfo(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "insert into course(\n" +
                    "                   course_name,\n" +
                    "                   brief,\n" +
                    "                   teacher_name,\n" +
                    "                   teacher_info,\n" +
                    "                   preview_first_field,\n" +
                    "                   preview_second_field,\n" +
                    "                   discounts,\n" +
                    "                   price,\n" +
                    "                   price_tag,\n" +
                    "                   share_image_title,\n" +
                    "                   share_title,\n" +
                    "                   share_description,\n" +
                    "                   course_description,\n" +
                    "                   course_img_url,\n" +
                    "                   status,\n" +
                    "                   create_time,\n" +
                    "                   update_time\n" +
                    ")values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            //准备参数
            Object[] params = {course.getCourse_name(), course.getBrief(),
                    course.getTeacher_name(), course.getTeacher_info(),
                    course.getPreview_first_field(), course.getPreview_second_field(),
                    course.getDiscounts(), course.getPrice(), course.getPrice_tag(),
                    course.getShare_image_title(), course.getShare_title(), course.getShare_description(),
                    course.getCourse_description(), course.getCourse_img_url(), course.getStatus(),
                    course.getCreate_time(), course.getUpdate_time()
            };
            //执行SQL操作
            int row = qr.update(sql, params);
            return row;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public Course findByCourseId(int id) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "select" +
                    "                       id," +
                    "                       course_name," +
                    "                       brief," +
                    "                       teacher_name," +
                    "                       teacher_info," +
                    "                       preview_first_field," +
                    "                       preview_second_field," +
                    "                       discounts," +
                    "                       price," +
                    "                       price_tag," +
                    "                       share_image_title," +
                    "                       share_title," +
                    "                       share_description," +
                    "                       course_description," +
                    "                       course_img_url" +
                    " from course where id = ?";
            Course course = qr.query(sql, new BeanHandler<>(Course.class), id);
            return course;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateCourseSalesInfo(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course set" +
                    "                   course_name=?," +
                    "                   brief=?," +
                    "                   teacher_name=?," +
                    "                   teacher_info=?," +
                    "                   preview_first_field=?," +
                    "                   preview_second_field=?," +
                    "                   discounts=?," +
                    "                   price=?," +
                    "                   price_tag=?," +
                    "                   share_image_title=?," +
                    "                   share_title=?," +
                    "                   share_description=?," +
                    "                   course_description=?," +
                    "                   course_img_url=?," +
                    "                   update_time =?" +
                    "where id = ?";
            Object[] params = {course.getCourse_name(), course.getBrief(), course.getTeacher_name(), course.getTeacher_info(),
                    course.getPreview_first_field(), course.getPreview_second_field(), course.getDiscounts(), course.getPrice(),
                    course.getPrice_tag(), course.getShare_image_title(), course.getShare_title(), course.getShare_description(),
                    course.getCourse_description(), course.getCourse_img_url(), course.getUpdate_time(), course.getId()};
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateCourseStatus(Course course) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course set status=?,update_time=? where id = ?";
            int update = qr.update(sql, course.getStatus(), course.getUpdate_time(), course.getId());
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
