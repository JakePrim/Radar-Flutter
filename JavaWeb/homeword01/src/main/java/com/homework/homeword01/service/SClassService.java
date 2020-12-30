package com.homework.homeword01.service;

import com.homework.homeword01.dao.SClassDao;
import com.homework.homeword01.factory.SClassFactory;
import com.homework.homeword01.pojo.SClass;

import java.sql.Connection;
import java.util.List;

public class SClassService {
    private SClassDao sClassDao;

    public SClassService() {
        sClassDao = SClassFactory.getSClassDao();
    }

    public List<SClass> findAll() {
        return sClassDao.findAll();
    }

    public SClass findById(int id) {
        return sClassDao.findById(id);
    }

    public List<SClass> findByName(String name) {
        return sClassDao.findByName(name);
    }

    public int add(SClass sClass) {
        return sClassDao.add(sClass);
    }

    public int delete(int id) {
        return sClassDao.delete(id);
    }

    public int update(SClass sClass) {
        return sClassDao.update(sClass);
    }

    public List<SClass> findByGrade(String grade) {
        return sClassDao.findByGrade(grade);
    }

    public List<SClass> findByTeacher(String teacher) {
        return sClassDao.findByTeacher(teacher);
    }

    public int updateNumAdd(int id) {
        return sClassDao.updateNumAdd(id);
    }

    public int updateNumSub(int id) {
        return sClassDao.updateNumSub(id);
    }

    public int updateNumAdd(Connection connection, int id) {
        return sClassDao.updateNumAdd(connection, id);
    }

    public int updateNumSub(Connection connection, int id) {
        return sClassDao.updateNumSub(connection, id);
    }
}
