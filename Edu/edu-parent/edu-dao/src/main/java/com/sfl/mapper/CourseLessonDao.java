package com.sfl.mapper;

import com.sfl.pojo.CourseLesson;

import java.util.List;

/**
 * @program: Edu
 * @Description: CourseLessonDao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 17:44
 * @PackageName: com.sfl.mapper
 * @ClassName: CourseLessonDao.java
 **/
public interface CourseLessonDao {
    List<CourseLesson> queryBySectionId(Integer section_id);
}
