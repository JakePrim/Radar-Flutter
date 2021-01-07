package com.prim.dao.impl;

import com.prim.bean.Elective;
import com.prim.dao.ElectiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ElectiveDaoImpl implements ElectiveDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(List<Elective> electives) {
        String sql = "insert into elective values(?,?,?,?)";
        List<Object[]> list = new ArrayList<>();
        for (Elective elective : electives) {
            Object[] args = new Object[4];
            args[0] = elective.getSid();
            args[1] = elective.getCid();
            args[2] = elective.geteTime();
            args[3] = elective.getScore();
        }
        jdbcTemplate.batchUpdate(sql, list);
    }

    @Override
    public void delete(int sid, int cid) {
        String sql = "delete from elective where sid=? and cid=?";
        jdbcTemplate.update(sql, sid, cid);
    }

    @Override
    public List<Map<String, Object>> findByStudent(int sid) {
        String sql = "select e.*,s.name sname,c.cname from elective e left join student s on e.sid=s.id left join course c on e.cid = c.id where e.sid=?";
        return jdbcTemplate.queryForList(sql, sid);
    }

    @Override
    public List<Map<String, Object>> findByCourse(int cid) {
        String sql = "select e.*,s.name sname,c.cname from elective e left join student s on e.sid=s.id left join course c on e.cid = c.id where e.cid=?";
        return jdbcTemplate.queryForList(sql, cid);
    }
}
