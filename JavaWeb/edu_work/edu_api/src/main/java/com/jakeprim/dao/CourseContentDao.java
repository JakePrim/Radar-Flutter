package com.jakeprim.dao;

import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;

import java.util.List;

/**
 * 课程内容管理 dao层接口
 */
public interface CourseContentDao {
    /**
     * 根据课程id查询章节和课时信息
     *
     * @param courseId
     * @return
     */
    List<Course_Section> findSectionAndLessonByCourseId(int courseId);

    /**
     * 根据章节id查询课时信息
     *
     * @param sectionId
     * @return
     */
    List<Course_Lesson> findLessonBySectionId(int sectionId);

    /**
     * 根据课程id 查询课程信息 仅包含id，name
     */
    Course findCourseByCourseId(int courseId);

    /**
     * 保存章节信息
     *
     * @param course_section
     * @return
     */
    int saveCourseSection(Course_Section course_section);

    int updateCourseSection(Course_Section course_section);

    /**
     * 修改章节状态
     */
    int updateSectionStatus(int id, int status);

    /**
     * 根据课程id查询所有章节信息：包括 id course_id section_name
     * 用于在添加或修改课时 显示章节列表信息 用于选择章节
     */
    List<Course_Section> findSectionByCourseId(int course_id);

    /**
     * 保存课时信息
     *
     * @param lesson
     * @return
     */
    int saveCourseLesson(Course_Lesson lesson);

    /**
     * 更新课时信息
     *
     * @param lesson
     * @return
     */
    int updateCourseLesson(Course_Lesson lesson);
}
