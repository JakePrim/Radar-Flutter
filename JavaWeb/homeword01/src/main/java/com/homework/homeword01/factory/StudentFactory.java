package com.homework.homeword01.factory;

import com.homework.homeword01.dao.StudentDao;
import com.homework.homeword01.dao.impl.StudentDaoImpl;

public class StudentFactory {
    public static StudentDao getStudentDao() {
        return new StudentDaoImpl();
    }
}
