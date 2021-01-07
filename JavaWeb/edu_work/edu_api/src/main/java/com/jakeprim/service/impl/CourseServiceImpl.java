package com.jakeprim.service.impl;

import com.jakeprim.base.StatusCode;
import com.jakeprim.dao.CourseDao;
import com.jakeprim.dao.impl.CourseDaoImpl;
import com.jakeprim.pojo.Course;
import com.jakeprim.service.CourseService;
import com.jakeprim.utils.DateUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程模块业务层的实现
 */
public class CourseServiceImpl implements CourseService {

    //创建course Dao
    CourseDao courseDao = new CourseDaoImpl();

    @Override
    public List<Course> findCourseList() {
        return courseDao.findCourseList();
    }

    @Override
    public List<Course> findByCourseNameAndStatus(String courseName, String status) {
        return courseDao.findByCourseNameAndStatus(courseName, status);
    }

    @Override
    public String saveCourseSalesInfo(Course course) {
        String dateFormart = DateUtils.getDateFormart();
        course.setCreate_time(dateFormart);
        course.setUpdate_time(dateFormart);
        course.setStatus(1);
        int row = courseDao.saveCourseSalesInfo(course);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public Course findCourseById(int id) {
        return courseDao.findByCourseId(id);
    }

    @Override
    public String updateCourseSalesInfo(Course course) {
        String dateFormart = DateUtils.getDateFormart();
        course.setUpdate_time(dateFormart);
        int row = courseDao.updateCourseSalesInfo(course);
        if (row > 0) {
            return StatusCode.SUCCESS.toString();
        } else {
            return StatusCode.FAIL.toString();
        }
    }

    @Override
    public Map<String, Integer> updateCourseStatus(Course course) {
        course.setUpdate_time(DateUtils.getDateFormart());
        int row = courseDao.updateCourseStatus(course);
        Map<String, Integer> map = new HashMap<>();
        if (row > 0) {
            if (course.getStatus() == 0) {
                map.put("status", 0);
            } else {
                map.put("status", 1);
            }
        }
        return map;
    }
}
