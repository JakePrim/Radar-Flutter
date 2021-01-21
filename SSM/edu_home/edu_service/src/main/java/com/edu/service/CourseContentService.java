package com.edu.service;

import com.edu.pojo.Course;
import com.edu.pojo.CourseLesson;
import com.edu.pojo.CourseSection;

import java.util.List;

public interface CourseContentService {
    List<CourseSection> findSectionAndLessonByCourseId(Integer courseId);

    Course findCourseByCourseId(Integer courseId);

    void saveSection(CourseSection courseSection);

    void updateSection(CourseSection courseSection);

    void updateSectionStatus(Integer id, Integer status);

    void saveLesson(CourseLesson courseLesson);

    void updateLesson(CourseLesson courseLesson);

    void updateLessonStatus(Integer id, Integer status);
}
