package com.prim.dao;

import com.prim.bean.Course;
import com.prim.bean.Student;

import java.util.List;

public interface CourseDao {
    void insert(Course course);

    void update(Course course);

    void delete(int id);

    Course findOne(int id);

    List<Course> findAll();
}
