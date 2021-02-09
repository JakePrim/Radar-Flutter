package com.sfl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfl.mapper.CourseDao;
import com.sfl.pojo.Course;
import com.sfl.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: Edu
 * @Description: 课程服务实现类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 22:12
 * @PackageName: com.sfl.service.impl
 * @ClassName: CourseServiceImpl.java
 **/
@Service(interfaceClass = CourseService.class)
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public List<Course> queryAllCourse() {
        List<Course> courses = courseDao.queryAll();
        return courses;
    }

    @Override
    public List<Course> getCourseByUserId(String user_id) {
        List<Integer> courseIds = courseDao.getCourseIdByUserId(user_id);
        if (courseIds != null) {
            List<Course> courses = courseDao.getCourseByUserId(courseIds);
            return courses;
        }
        return null;
    }

    @Override
    public Course getCourseById(Integer id) {
        Course course = courseDao.getCourseById(id);
        return course;
    }
}
