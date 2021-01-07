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

    /**
     * 条件查询课程列表信息
     */
    List<Course> findByCourseNameAndStatus(String courseName, String status);

    /**
     * 保存课程营销信息
     *
     * @param course
     * @return
     */
    int saveCourseSalesInfo(Course course);

    /**
     * 根据id查找课程营销信息
     *
     * @param id
     * @return
     */
    Course findByCourseId(int id);

    int updateCourseSalesInfo(Course course);
}
