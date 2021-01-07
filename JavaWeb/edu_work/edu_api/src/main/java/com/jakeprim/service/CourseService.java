package com.jakeprim.service;

import com.jakeprim.pojo.Course;

import java.util.List;

/**
 * 课程模块业务层的接口
 */
public interface CourseService {
    List<Course> findCourseList();

    List<Course> findByCourseNameAndStatus(String courseName, String status);

    String saveCourseSalesInfo(Course course);

    Course findCourseById(int id);

    String updateCourseSalesInfo(Course course);
}
