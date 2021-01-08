package com.jakeprim.service;

import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;

import java.util.List;

public interface CourseContentService {
    List<Course_Section> findSectionAndLessonByCourseId(int id);

    Course findCourseByCourseId(int courseId);

    String saveCourseSection(Course_Section course_section);

    String updateCourseSection(Course_Section course_section);

    String updateSectionStatus(int id, int status);

    String saveCourseLesson(Course_Lesson lesson);

    String updateCourseLesson(Course_Lesson lesson);
}
