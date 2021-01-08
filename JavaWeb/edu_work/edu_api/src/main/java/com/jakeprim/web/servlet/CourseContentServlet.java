package com.jakeprim.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jakeprim.base.BaseServlet;
import com.jakeprim.pojo.Course;
import com.jakeprim.pojo.Course_Lesson;
import com.jakeprim.pojo.Course_Section;
import com.jakeprim.service.CourseContentService;
import com.jakeprim.service.impl.CourseContentServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

@WebServlet("/courseContent")
public class CourseContentServlet extends BaseServlet {
    //展示对应课程的章节与课时信息
    public void findSectionAndLessonByCourseId(HttpServletRequest request, HttpServletResponse response) {
        try {
            String courseId = request.getParameter("course_id");
            CourseContentService service = new CourseContentServiceImpl();
            List<Course_Section> sections = service.findSectionAndLessonByCourseId(Integer.parseInt(courseId));
            String result = JSON.toJSONString(sections);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据课程id回显课程名称
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String course_id = request.getParameter("course_id");
            CourseContentService service = new CourseContentServiceImpl();
            Course course = service.findCourseByCourseId(Integer.parseInt(course_id));
            //过滤显示的字段
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name");
            String result = JSON.toJSONString(course, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存&修改章节信息
     *
     * @param request
     * @param response
     */
    public void saveOrUpdateSection(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1. 获取参数
            Map<String, Object> paramsMap = (Map<String, Object>) request.getAttribute("params");
            //2. 创建一个章节对象
            Course_Section section = new Course_Section();
            //3. BeanUtils 添加到对象中的
            BeanUtils.populate(section, paramsMap);

            CourseContentService service = new CourseContentServiceImpl();
            String result = null;
            if (section.getId() != 0) {
                //修改章节信息
                result = service.updateCourseSection(section);
            } else {
                //新增章节信息
                result = service.saveCourseSection(section);
            }
            response.getWriter().print(result);
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存&修改课时信息
     *
     * @param request
     * @param response
     */
    public void saveOrUpdateLesson(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> paramsMap = (Map<String, Object>) request.getAttribute("params");
            Course_Lesson course_lesson = new Course_Lesson();
            //将请求参数添加到对象中
            BeanUtils.populate(course_lesson, paramsMap);
            CourseContentService service = new CourseContentServiceImpl();
            String result = null;
            if (course_lesson.getId() != 0) {
                result = service.updateCourseLesson(course_lesson);
            } else {
                //新增课时信息
                result = service.saveCourseLesson(course_lesson);
            }
            response.getWriter().print(result);
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新课程状态
     */
    public void updateSectionStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int status = Integer.parseInt(request.getParameter("status"));
            CourseContentService service = new CourseContentServiceImpl();
            String result = service.updateSectionStatus(id, status);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
