package com.edu.service.impl;

import com.edu.dao.CourseContentMapper;
import com.edu.dao.CourseLessonMapper;
import com.edu.pojo.Course;
import com.edu.pojo.CourseLesson;
import com.edu.pojo.CourseSection;
import com.edu.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    @Override
    public List<CourseSection> findSectionAndLessonByCourseId(Integer courseId) {
        List<CourseSection> sections = courseContentMapper.findSectionAndLessonByCourseId(courseId);
        return sections;
    }

    @Override
    public Course findCourseByCourseId(Integer courseId) {
        Course course = courseContentMapper.findCourseByCourseId(courseId);
        return course;
    }

    @Override
    public void saveSection(CourseSection courseSection) {
        Date date = new Date();
        courseSection.setCreateTime(date);
        courseSection.setUpdateTime(date);
        courseSection.setIsDe(0);
        courseSection.setStatus(0);
        courseContentMapper.saveCourseSectionByCourseId(courseSection);
    }

    @Override
    public void updateSection(CourseSection courseSection) {
        courseSection.setUpdateTime(new Date());
        courseContentMapper.updateCourseSectionById(courseSection);
    }

    @Override
    public void updateSectionStatus(Integer id, Integer status) {
        CourseSection courseSection = new CourseSection();
        courseSection.setId(id);
        courseSection.setStatus(status);
        courseSection.setUpdateTime(new Date());
        courseContentMapper.updateSectionStatusById(courseSection);
    }

    @Override
    public void saveLesson(CourseLesson courseLesson) {
        Date date = new Date();
        courseLesson.setIsDel(0);
        courseLesson.setStatus(0);
        courseLesson.setCreateTime(date);
        courseLesson.setUpdateTime(date);
        courseLessonMapper.saveLesson(courseLesson);
    }

    @Override
    public void updateLesson(CourseLesson courseLesson) {
        Date date = new Date();
        courseLesson.setUpdateTime(date);

        courseLessonMapper.updateLesson(courseLesson);
    }

    @Override
    public void updateLessonStatus(Integer id, Integer status) {
        CourseLesson courseLesson = new CourseLesson();
        courseLesson.setId(id);
        courseLesson.setStatus(status);
        courseLesson.setUpdateTime(new Date());

        courseLessonMapper.updateLessonStatus(courseLesson);
    }
}
