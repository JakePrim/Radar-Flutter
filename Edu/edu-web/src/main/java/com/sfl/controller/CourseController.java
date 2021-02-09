package com.sfl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfl.pojo.Course;
import com.sfl.pojo.ResultDTO;
import com.sfl.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: edu-web
 * @Description: 课程api相关
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 22:14
 * @PackageName: com.sfl.controller
 * @ClassName: CourseController.java
 **/
@RestController
@RequestMapping("/course")
public class CourseController {

    @Reference
    private CourseService courseService;

    @GetMapping("/getAllCourse")
    public ResultDTO<List<Course>> getAllCourse() {
        List<Course> courses = courseService.queryAllCourse();
        ResultDTO<List<Course>> success = ResultDTO.createSuccess("查询成功", courses);
        return success;
    }

    @GetMapping("/getCourseByUserId/{userId}")
    public ResultDTO<List<Course>> getCourseByUserId(@PathVariable("userId") String userId) {
        List<Course> courses = courseService.getCourseByUserId(userId);
        ResultDTO<List<Course>> success = ResultDTO.createSuccess("查询成功", courses);
        return success;
    }

    @GetMapping("/getCourseById/{id}")
    public ResultDTO<Course> getCourseById(@PathVariable("id") Integer id) {
        Course courses = courseService.getCourseById(id);
        ResultDTO<Course> success = ResultDTO.createSuccess("查询成功", courses);
        return success;
    }


}
