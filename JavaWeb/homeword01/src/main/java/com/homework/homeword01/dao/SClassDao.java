package com.homework.homeword01.dao;

import com.homework.homeword01.pojo.SClass;

import java.util.List;

public interface SClassDao {
    List<SClass> findAll();

    SClass findById(int id);

    SClass findByName(String name);

    SClass findByGrade(String grade);

    SClass findByTeacher(String teacher);

    int add(SClass sClass);

    int delete(int id);

    int update(SClass sClass);
}
