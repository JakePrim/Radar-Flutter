package com.prim.dao.impl;

import com.prim.bean.Student;
import com.prim.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author prim
 */
@Repository
public class StudentDaoImpl implements StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Student student) {
        String sql = "insert into student(name,sex,birthday) values(?,?,?)";
        jdbcTemplate.update(sql, student.getName(), student.getSex(), student.getBorn());
    }

    @Override
    public void update(Student student) {
        String sql = "update student set name=?,sex=?,birthday=? where id = ?";
        jdbcTemplate.update(sql, student.getName(), student.getSex(), student.getBorn(), student.getId());

    }

    @Override
    public void delete(int id) {
        String sql = "delete from student where id =?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Student findOne(int id) {
        String sql = "select * from student where id=?";
        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
    }

    @Override
    public List<Student> findAll() {
        String sql = "select * from student";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    private static class StudentRowMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            Student student1 = new Student();
            student1.setId(resultSet.getInt("id"));
            student1.setName(resultSet.getString("name"));
            student1.setSex(resultSet.getString("sex"));
            student1.setBorn(resultSet.getDate("birthday"));
            return student1;
        }
    }
}
