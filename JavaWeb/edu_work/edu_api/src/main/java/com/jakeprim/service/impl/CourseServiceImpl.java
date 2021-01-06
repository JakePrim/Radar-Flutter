package com.jakeprim.service.impl;

import com.jakeprim.dao.CourseDao;
import com.jakeprim.dao.impl.CourseDaoImpl;
import com.jakeprim.pojo.Course;
import com.jakeprim.service.CourseService;

import java.util.List;

/**
 * 课程模块业务层的实现
 */
public class CourseServiceImpl implements CourseService {

    //创建course Dao
    CourseDao courseDao = new CourseDaoImpl();

    @Override
    public List<Course> findCourseList() {
        return courseDao.findCourseList();
    }
}
