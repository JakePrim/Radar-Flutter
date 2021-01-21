package com.edu.service.impl;

import com.edu.dao.CourseMapper;
import com.edu.pojo.Course;
import com.edu.pojo.Teacher;
import com.edu.pojo.bo.CourseBo;
import com.edu.pojo.vo.CourseVo;
import com.edu.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findCourseByCondition(CourseVo courseVo) {
        List<Course> courses = courseMapper.findCourseByCondition(courseVo);
        return courses;
    }

    @Override
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //1. 封装Course实体类
        Course course = new Course();
        //将courseVo中的属性值 赋给Course实体中
        BeanUtils.copyProperties(course, courseVo);
        //补全信息：创建时间和更新时间
        Date date = new Date();
        course.setCreateTime(date);
        course.setUpdateTime(date);
        //2. 调用dao 保存课程
        courseMapper.saveCourse(course);
        //3. 获取课程id 封装teacher实体
        int courseId = course.getId();
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, courseVo);
        teacher.setCourseId(courseId);
        teacher.setCreateTime(date);
        teacher.setUpdateTime(date);
        teacher.setIsDel(0);
        //4. 调用dao 保存讲师信息
        courseMapper.saveTeacher(teacher);
    }

    @Override
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //1. 封装Course实体
        Course course = new Course();
        BeanUtils.copyProperties(course, courseVo);

        //补全信息：创建时间和更新时间
        Date date = new Date();
        course.setUpdateTime(date);
        //2. 更新课程信息
        courseMapper.updateCourse(course);

        //3. 获取课程id 封装teacher实体
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, courseVo);

        teacher.setCourseId(courseVo.getId());
        teacher.setUpdateTime(date);

        //4. 调用dao 保存讲师信息
        courseMapper.updateTeacher(teacher);
    }

    @Override
    public CourseBo findByCourseId(Integer id) {
        CourseBo courseBo = courseMapper.findByCourseId(id);
        return courseBo;
    }

    @Override
    public void updateCourseStatus(Integer courseId, Integer status) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(status);
        course.setUpdateTime(new Date());
        courseMapper.updateCourseStatus(course);
    }
}
