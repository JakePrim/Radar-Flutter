package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Course;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.bo.CourseBo;
import com.edu.pojo.vo.CourseVo;
import com.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    /**
     * @param courseVo
     * @return
     * @RequestBody 将json的参数 封装成实体
     */
    @PostMapping("/findAllCourse")
    public ResponseResult findAllCourse(@RequestBody CourseVo courseVo) {
        List<Course> courses = courseService.findCourseByCondition(courseVo);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), courses);
        return result;
    }

    @PostMapping("/courseUpload")
    public ResponseResult courseUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map = fileUpload("upload/course/", file, request);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return result;
    }

    /**
     * 保存和更新课程api
     *
     * @param courseVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PostMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) {
        try {
            if (courseVo.getId() != null) {
                courseService.updateCourseOrTeacher(courseVo);
            } else {
                courseService.saveCourseOrTeacher(courseVo);
            }
            ResponseResult result = new ResponseResult(true,
                    StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id) {
        CourseBo courseBo = courseService.findByCourseId(id);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(),
                        StateCode.SUCCESS.getMsg(), courseBo);
        return result;
    }

    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id, Integer status) {
        courseService.updateCourseStatus(id, status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(),
                        StateCode.SUCCESS.getMsg(), map);
        return result;
    }
}
