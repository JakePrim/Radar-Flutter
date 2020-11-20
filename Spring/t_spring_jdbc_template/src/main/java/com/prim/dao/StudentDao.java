package com.prim.dao;

import com.prim.bean.Student;

import java.util.List;

/**
 * @author prim
 */
public interface StudentDao {
    void insert(Student student);

    void update(Student student);

    void delete(int id);

    Student findOne(int id);

    List<Student> findAll();
}
