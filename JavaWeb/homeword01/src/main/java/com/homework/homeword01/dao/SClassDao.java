package com.homework.homeword01.dao;

import com.homework.homeword01.pojo.SClass;

import java.sql.Connection;
import java.util.List;

public interface SClassDao {
    List<SClass> findAll();

    SClass findById(int id);

    List<SClass> findByName(String name);

    List<SClass> findByGrade(String grade);

    List<SClass> findByTeacher(String teacher);

    int add(SClass sClass);

    int delete(int id);

    int update(SClass sClass);

    int updateNumAdd(int id);

    int updateNumSub(int id);

    int updateNumAdd(Connection connection,int id);

    int updateNumSub(Connection connection,int id);
}
