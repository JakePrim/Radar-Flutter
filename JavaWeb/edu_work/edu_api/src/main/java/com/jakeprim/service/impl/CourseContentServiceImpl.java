package com.jakeprim.service.impl;

import com.jakeprim.base.StatusCode;
import com.jakeprim.dao.CourseContentDao;
import com.jakeprim.dao.impl.CourseContentDaoImpl;
import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;
import com.jakeprim.service.CourseContentService;
import com.jakeprim.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 课程内容管理service层接口
 */
public class CourseContentServiceImpl implements CourseContentService {
    CourseContentDao dao = new CourseContentDaoImpl();

    @Override
    public List<Course_Section> findSectionAndLessonByCourseId(int id) {
        return dao.findSectionAndLessonByCourseId(id);
    }

    @Override
    public Course findCourseByCourseId(int courseId) {
        return dao.findCourseByCourseId(courseId);
    }

    @Override
    public String saveCourseSection(Course_Section course_section) {
        String dateFormart = DateUtils.getDateFormart();
        course_section.setCreate_time(dateFormart);
        course_section.setUpdate_time(dateFormart);
        course_section.setStatus(2);//默认状态为  2 已发布   0 隐藏   1 待更新

        int row = dao.saveCourseSection(course_section);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public String updateCourseSection(Course_Section course_section) {
        String dateFormart = DateUtils.getDateFormart();
        course_section.setUpdate_time(dateFormart);

        int row = dao.updateCourseSection(course_section);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public String updateSectionStatus(int id, int status) {
        int row = dao.updateSectionStatus(id, status);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public String saveCourseLesson(Course_Lesson lesson) {
        String dateFormart = DateUtils.getDateFormart();
        lesson.setCreate_time(dateFormart);
        lesson.setUpdate_time(dateFormart);
        lesson.setStatus(2);
        int row = dao.saveCourseLesson(lesson);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public String updateCourseLesson(Course_Lesson lesson) {
        String dateFormart = DateUtils.getDateFormart();
        lesson.setUpdate_time(dateFormart);
        int row = dao.updateCourseLesson(lesson);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }
}
