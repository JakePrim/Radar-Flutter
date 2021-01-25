package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Course;
import com.edu.pojo.CourseLesson;
import com.edu.pojo.CourseSection;
import com.edu.pojo.ResponseResult;
import com.edu.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId) {
        List<CourseSection> sections = courseContentService.findSectionAndLessonByCourseId(courseId);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), sections);
        return result;
    }

    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId) {
        Course course = courseContentService.findCourseByCourseId(courseId);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), course);
        return result;
    }

    /**
     * 保存和更新章节信息
     *
     * @param courseSection
     * @return
     */
    @PostMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection) {
        if (courseSection.getId() != null) {
            courseContentService.updateSection(courseSection);
        } else {
            courseContentService.saveSection(courseSection);
        }
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return result;
    }

    /**
     * 修改章节的状态
     */
    @GetMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(Integer id, Integer status) {
        courseContentService.updateSectionStatus(id, status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return result;
    }

    @PostMapping("/saveOrUpdateLesson")
    public ResponseResult saveOrUpdateLesson(@RequestBody CourseLesson courseLesson) {
        if (courseLesson.getId() != null) {
            courseContentService.updateLesson(courseLesson);
        } else {
            courseContentService.saveLesson(courseLesson);
        }
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return result;
    }

    @GetMapping("/updateLessonStatus")
    public ResponseResult updateLessonStatus(Integer id, Integer status) {
        courseContentService.updateLessonStatus(id, status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return result;
    }
}
