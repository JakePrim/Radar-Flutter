package com.prim.test.dao;

import com.jakeprim.dao.CourseDao;
import com.jakeprim.dao.impl.CourseDaoImpl;
import com.jakeprim.pojo.Course;
import org.junit.Test;

import java.util.List;

public class TestCourse {
    @Test
    public void findCourseList(){
        CourseDao courseDao = new CourseDaoImpl();
        List<Course> courseList = courseDao.findCourseList();
        for (Course course : courseList) {
            System.out.println(course);
        }
    }
}
