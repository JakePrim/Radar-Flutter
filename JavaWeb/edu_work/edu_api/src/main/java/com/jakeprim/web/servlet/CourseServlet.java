package com.jakeprim.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.jakeprim.base.BaseServlet;
import com.jakeprim.pojo.Course;
import com.jakeprim.service.CourseService;
import com.jakeprim.service.impl.CourseServiceImpl;
import com.jakeprim.utils.DateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 课程模块的Web层实现
 */
@WebServlet(name = "CourseServlet", urlPatterns = "/course")
public class CourseServlet extends BaseServlet {

    /**
     * 查询课程信息
     */
    public void findCourseList(HttpServletRequest request, HttpServletResponse response) {
        //1. 接收参数 当前功能不需要参数
        //2. 业务处理
        CourseService courseService = new CourseServiceImpl();
        List<Course> courseList = courseService.findCourseList();

        // 处理响应的结果 SimplePropertyPreFilter 指定要转换的json字段
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name", "price", "sort_num", "status");
        //3. 响应结果
        String result = JSON.toJSONString(courseList, filter);
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 条件查询课程信息
     *
     * @param request
     * @param response
     */
    public void findByCourseNameAndStatus(HttpServletRequest request, HttpServletResponse response) {
        String course_name = request.getParameter("course_name");
        String status = request.getParameter("status");
        CourseService courseService = new CourseServiceImpl();
        List<Course> courseList = courseService.findByCourseNameAndStatus(course_name, status);
        // 处理响应的结果 SimplePropertyPreFilter 指定要转换的json字段
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class, "id", "course_name", "price", "sort_num", "status");
        //3. 响应结果
        String result = JSON.toJSONString(courseList, filter);
        try {
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存课程营销信息
     *
     * @param request
     * @param response
     */
    public void findCourseById(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("id");
            CourseService courseService = new CourseServiceImpl();
            Course course = courseService.findCourseById(Integer.parseInt(id));
            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Course.class,
                    "id", "course_name", "brief", "teacher_name", "teacher_info"
                    , "preview_first_field", "preview_second_field", "discounts", "price", "price_tag",
                    "share_image_title", "share_title", "share_description", "course_description", "course_img_url");
            String result = JSON.toJSONString(course, filter);
            response.getWriter().print(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
