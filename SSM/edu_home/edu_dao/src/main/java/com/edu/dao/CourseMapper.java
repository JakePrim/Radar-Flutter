package com.edu.dao;

import com.edu.pojo.Course;
import com.edu.pojo.Teacher;
import com.edu.pojo.bo.CourseBo;
import com.edu.pojo.vo.CourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    /*
     * 课程查询
     * */
    List<Course> findCourseByCondition(CourseVo courseVo);

    /**
     * 新增课程信息
     */
    void saveCourse(Course course);

    /**
     * 新增讲师信息
     */
    void saveTeacher(Teacher teacher);

    /**
     * 查询课程信息
     *
     * @param id
     * @return
     */
    CourseBo findByCourseId(Integer id);

    /**
     * 更新课程信息
     */
    void updateCourse(Course course);

    /**
     * 更新讲师信息
     *
     * @param teacher
     */
    void updateTeacher(Teacher teacher);

    /**
     * 更新课程状态
     */
    void updateCourseStatus(Course course);
}
