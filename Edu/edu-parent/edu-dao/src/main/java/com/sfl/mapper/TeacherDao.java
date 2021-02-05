package com.sfl.mapper;

import com.sfl.pojo.Teacher;

/**
 * @program: Edu
 * @Description: Teacherdao
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 17:43
 * @PackageName: com.sfl.mapper
 * @ClassName: TeacherDao.java
 **/
public interface TeacherDao {
    Teacher queryByCourseId(Integer course_id);
}
