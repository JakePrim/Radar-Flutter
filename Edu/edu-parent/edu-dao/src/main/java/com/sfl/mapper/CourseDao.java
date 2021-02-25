package com.sfl.mapper;

import com.sfl.pojo.Course;

import java.util.List;

/**
 * @program: Edu
 * @Description: 课程dao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 17:41
 * @PackageName: com.sfl.mapper
 * @ClassName: CourseDao.java
 **/
public interface CourseDao {
    /**
     * 查询所有课程
     * @return
     */
    List<Course> queryAll();

    /**
     * 查询用户已购课程
     * @param user_id
     * @return
     */
    List<Integer> getCourseIdByUserId(String user_id);

    /**
     * 查询用户已购课程的id
     * @param ids
     * @return
     */
    List<Course> getCourseByUserId(List<Integer> ids);

    /**
     * 查询某门课程的详细信息
     * @param id
     * @return
     */
    Course getCourseById(Integer id);
}
