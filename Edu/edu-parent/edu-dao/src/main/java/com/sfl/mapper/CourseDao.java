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
    List<Course> queryAll();
}
