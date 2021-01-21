package com.edu.dao;

import com.edu.pojo.Course;
import com.edu.pojo.CourseSection;

import java.util.List;

public interface CourseContentMapper {
    /**
     * 查询章节信息及关联的课时信息
     */
    List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    Course findCourseByCourseId(Integer courseId);

    void saveCourseSectionByCourseId(CourseSection courseSection);

    void updateCourseSectionById(CourseSection courseSection);

    void updateSectionStatusById(CourseSection courseSection);
}
