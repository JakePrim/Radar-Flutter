package com.jakeprim.dao.impl;

import com.jakeprim.dao.CourseContentDao;
import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;
import com.jakeprim.utils.DateUtils;
import com.jakeprim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CourseContentDaoImpl implements CourseContentDao {
    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int courseId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "select id,course_id,section_name,description,order_num,status " +
                    "from course_section where course_id=? and is_del=0";
            List<Course_Section> sections = qr.query(sql, new BeanListHandler<>(Course_Section.class), courseId);
            //根据章节id查询课时信息
            for (Course_Section section : sections) {
                List<Course_Lesson> lessons = findLessonBySectionId(section.getId());
                section.getLessonList().addAll(lessons);
            }
            return sections;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Course_Lesson> findLessonBySectionId(int sectionId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "select id,course_id,section_id,theme,duration,is_free,order_num,status " +
                    "from course_lesson where section_id=? and is_del=0";
            List<Course_Lesson> lessons = qr.query(sql, new BeanListHandler<>(Course_Lesson.class), sectionId);
            return lessons;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Course findCourseByCourseId(int courseId) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "select id,course_name from course where id = ?";
            Course course = qr.query(sql, new BeanHandler<>(Course.class), courseId);
            return course;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveCourseSection(Course_Section course_section) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "insert into course_section(course_id, section_name, description, order_num, status, create_time, update_time)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {course_section.getCourse_id(),
                    course_section.getSection_name(), course_section.getDescription(),
                    course_section.getOrder_num(), course_section.getStatus(),
                    course_section.getCreate_time(), course_section.getUpdate_time()};
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateCourseSection(Course_Section course_section) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_section\n" +
                    "set section_name=?,\n" +
                    "    description=?,\n" +
                    "    order_num=?,\n" +
                    "    update_time=?\n" +
                    "where id = ?";
            int update = qr.update(sql, course_section.getSection_name(), course_section.getDescription(),
                    course_section.getOrder_num(),
                    course_section.getUpdate_time(),
                    course_section.getId());
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateSectionStatus(int id, int status) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_section set status=?,update_time=? where id=?";
            int update = qr.update(sql, status, DateUtils.getDateFormart(), id);
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Course_Section> findSectionByCourseId(int course_id) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "select id, course_id, section_name\n" +
                    "from course_section\n" +
                    "where course_id = ?";
            List<Course_Section> sections = qr.query(sql,
                    new BeanListHandler<>(Course_Section.class), course_id);
            return sections;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int saveCourseLesson(Course_Lesson lesson) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "insert into course_lesson(course_id,section_id,theme,duration," +
                    "is_free,order_num,status,create_time,update_time)\n" +
                    "values (?,?,?,?,?,?,?,?,?);";
            Object[] params = {
                    lesson.getCourse_id(),
                    lesson.getSection_id(),
                    lesson.getTheme(),
                    lesson.getDuration(),
                    lesson.getIs_free(),
                    lesson.getOrderNum(),
                    lesson.getStatus(),
                    lesson.getCreate_time(),
                    lesson.getUpdate_time()
            };
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateCourseLesson(Course_Lesson lesson) {
        try {
            QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
            String sql = "update course_lesson\n" +
                    "set section_id=?,\n" +
                    "    theme=?,\n" +
                    "    duration=?,\n" +
                    "    is_free=?,\n" +
                    "    order_num=?,\n" +
                    "    update_time=?\n" +
                    "where id=? and course_id=?";
            Object[] params = {
                    lesson.getSection_id(),
                    lesson.getTheme(),
                    lesson.getDuration(),
                    lesson.getIs_free(),
                    lesson.getOrderNum(),
                    lesson.getUpdate_time(),
                    lesson.getId(),
                    lesson.getCourse_id()
            };
            int update = qr.update(sql, params);
            return update;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
