package com.edu.service;

import com.edu.pojo.Course;
import com.edu.pojo.bo.CourseBo;
import com.edu.pojo.vo.CourseVo;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CourseService {
    List<Course> findCourseByCondition(CourseVo courseVo);

    void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;

    void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException;

    CourseBo findByCourseId(Integer id);

    void updateCourseStatus(Integer courseId, Integer status);
}
