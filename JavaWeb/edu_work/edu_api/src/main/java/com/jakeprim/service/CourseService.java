package com.jakeprim.service;

import com.jakeprim.pojo.Course;

import java.util.List;

/**
 * 课程模块业务层的接口
 */
public interface CourseService {
    List<Course> findCourseList();
}
