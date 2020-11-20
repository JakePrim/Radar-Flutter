package com.prim.dao.impl;

import com.prim.bean.Course;
import com.prim.bean.Student;
import com.prim.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Course course) {
        String sql = "insert into course(cname,fraction) values(?,?)";
        jdbcTemplate.update(sql, course.getCname(), course.getFraction());
    }

    @Override
    public void update(Course course) {
        String sql = "update course set cname=?,fraction=? where id = ?";
        jdbcTemplate.update(sql, course.getCname(), course.getFraction(), course.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from course where id =?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Course findOne(int id) {
        String sql = "select * from course where id=?";
        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
    }

    @Override
    public List<Course> findAll() {
        String sql = "select * from course";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    private static class StudentRowMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet resultSet, int i) throws SQLException {
            Course course = new Course();
            course.setId(resultSet.getInt("id"));
            course.setCname(resultSet.getString("cname"));
            course.setFraction(resultSet.getInt("fraction"));
            return course;
        }
    }
}
