package com.sfl.mapper;

import com.sfl.pojo.CourseSection;

import java.util.List;

/**
 * @program: Edu
 * @Description: 章节dao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 17:42
 * @PackageName: com.sfl.mapper
 * @ClassName: CourseSectionDao.java
 **/
public interface CourseSectionDao {
    List<CourseSection> queryByCourseId(Integer course_id);
}
