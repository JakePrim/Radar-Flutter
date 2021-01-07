package com.prim.dao;

import com.prim.bean.Elective;
import com.prim.bean.Student;

import java.util.List;
import java.util.Map;

/**
 * @author prim
 */
public interface ElectiveDao {
    void insert(List<Elective> electives);

//    void update(Student student);

    void delete(int sid,int cid);

//    Elective findOne(int id);

    List<Map<String, Object>> findByStudent(int sid);

    List<Map<String, Object>> findByCourse(int cid);
}
