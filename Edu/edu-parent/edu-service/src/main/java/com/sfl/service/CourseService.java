package com.sfl.service;

import com.sfl.pojo.Course;

import java.util.List;

/**
 * @program: Edu
 * @Description: 课程服务
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 22:12
 * @PackageName: com.sfl.service
 * @ClassName: CourseService.java
 **/
public interface CourseService {
    List<Course> queryAllCourse();

    List<Course> getCourseByUserId(String user_id);

    /**
     * 查询某门课程的消息信息
     *
     * @param id
     * @return
     */
    Course getCourseById(Integer id);
}
