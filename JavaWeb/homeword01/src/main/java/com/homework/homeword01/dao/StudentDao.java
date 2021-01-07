package com.homework.homeword01.dao;

import com.homework.homeword01.pojo.PageHelp;
import com.homework.homeword01.pojo.Student;

import java.util.List;

public interface StudentDao {
    List<Student> findAll();

    PageHelp<Student> findPage(int page, int pageSize);

    Student findById(int id);

    int add(Student student);

    int delete(int id);

    int update(Student student);
}
