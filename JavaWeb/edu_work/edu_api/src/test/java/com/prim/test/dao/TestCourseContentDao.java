package com.prim.test.dao;

import com.jakeprim.dao.CourseContentDao;
import com.jakeprim.dao.impl.CourseContentDaoImpl;
import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;
import com.jakeprim.utils.DateUtils;
import org.junit.Test;

import java.util.List;

public class TestCourseContentDao {
    CourseContentDao courseContentDao = new CourseContentDaoImpl();

    @Test
    public void testFindSectionAndLessonByCourseId() {
        List<Course_Section> sections = courseContentDao.findSectionAndLessonByCourseId(59);
        for (Course_Section section : sections) {
            System.out.println(section.getSection_name());
            for (Course_Lesson lesson : section.getLessonList()) {
                System.out.println(lesson.getTheme());
            }
        }
    }

    @Test
    public void testSaveCourseSection() {
        Course course = courseContentDao.findCourseByCourseId(59);
        System.out.println(course.getId() + ":" + course.getCourse_name());
        Course_Section course_section = new Course_Section();
        course_section.setCourse_id(course.getId());
        course_section.setSection_name("Vuex状态管理");
        course_section.setDescription("Vuex状态管理描述");
        course_section.setStatus(1);
        course_section.setOrder_num(9);
        String dateFormart = DateUtils.getDateFormart();
        course_section.setCreate_time(dateFormart);
        course_section.setUpdate_time(dateFormart);
        int row = courseContentDao.saveCourseSection(course_section);
        System.out.println("影响行数:" + row);
    }

    @Test
    public void testUpdateSection() {
        Course_Section course_section = new Course_Section();
        course_section.setId(44);
        course_section.setSection_name("Vuex状态管理及Vuex原理");
        course_section.setDescription("Vuex状态管理描述");
        course_section.setOrder_num(9);
        course_section.setUpdate_time(DateUtils.getDateFormart());
        int row = courseContentDao.updateCourseSection(course_section);
        System.out.println("影响行数:" + row);
    }

    @Test
    public void testUpdateSectionStatus() {
        int row = courseContentDao.updateSectionStatus(41, 1);
        System.out.println("影响行数:" + row);
    }

    @Test
    public void testSaveCourseLesson() {
        Course_Lesson course_lesson = new Course_Lesson();
        course_lesson.setCourse_id(1);
        course_lesson.setSection_id(1);
        course_lesson.setTheme("11讲 微服务架构");
        course_lesson.setDuration(60);
        course_lesson.setIs_free(1);
        course_lesson.setOrderNum(11);
        String dateFormart = DateUtils.getDateFormart();
        course_lesson.setCreate_time(dateFormart);
        course_lesson.setUpdate_time(dateFormart);
        int row = courseContentDao.saveCourseLesson(course_lesson);
        System.out.println("影响行数:" + row);
    }

    @Test
    public void testUpdateCourseLesson() {
        Course_Lesson course_lesson = new Course_Lesson();
        course_lesson.setId(43);
        course_lesson.setCourse_id(1);
        course_lesson.setSection_id(2);
        course_lesson.setTheme("12讲 微服务架构");
        course_lesson.setDuration(60);
        course_lesson.setIs_free(1);
        course_lesson.setOrderNum(11);
        String dateFormart = DateUtils.getDateFormart();
        course_lesson.setUpdate_time(dateFormart);
        int row = courseContentDao.updateCourseLesson(course_lesson);
        System.out.println("影响行数:" + row);
    }
}
