package com.sfl.mapper;

import com.sfl.pojo.CourseMedia;

/**
 * @program: Edu
 * @Description: CourseMediaDao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 17:45
 * @PackageName: com.sfl.mapper
 * @ClassName: CourseMediaDao.java
 **/
public interface CourseMediaDao {
    CourseMedia queryByLessonId(Integer lesson_id);
}
