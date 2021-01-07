package com.homework.homeword01.service;

import com.homework.homeword01.dao.StudentDao;
import com.homework.homeword01.factory.StudentFactory;
import com.homework.homeword01.pojo.PageHelp;
import com.homework.homeword01.pojo.Student;

import javax.naming.spi.StateFactory;
import java.util.List;

public class StudentService {
    private StudentDao studentDao;

    public StudentService() {
        studentDao = StudentFactory.getStudentDao();
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public Student findById(int id) {
        return studentDao.findById(id);
    }

    public int add(Student student) {
        return studentDao.add(student);
    }

    public int delete(int id) {
        return studentDao.delete(id);
    }

    public int update(Student student) {
        return studentDao.update(student);
    }

    public PageHelp<Student> findPage(int page, int pageSize) {
        return studentDao.findPage(page, pageSize);
    }
}
