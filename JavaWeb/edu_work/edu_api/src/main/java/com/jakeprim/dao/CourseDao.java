package com.jakeprim.dao;

import com.jakeprim.pojo.Course;

import java.util.List;

/**
 * 课程模块DAO层接口
 */
public interface CourseDao {

    /**
     * 查询课程列表信息
     */
    List<Course> findCourseList();
}
