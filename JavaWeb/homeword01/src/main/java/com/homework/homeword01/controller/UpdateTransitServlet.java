package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.SClassService;
import com.homework.homeword01.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 更新信息中转页面
 */
@WebServlet(name = "UpdateTransitServlet", urlPatterns = "/updateTransit.do")
public class UpdateTransitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 首先查询学生信息
        String id = request.getParameter("id");
        if (null != id && !"".equals(id)) {
            StudentService service = new StudentService();
            Student student = service.findById(Integer.parseInt(id));
            //查询所有班级信息
            SClassService sClassService = new SClassService();
            List<SClass> classes = sClassService.findAll();
            // 将所有的班级信息添加到请求参数中
            request.setAttribute("classes", classes);
            //2. 将学生信息添加到请求参数中通过转发 显示学生信息 用户修改
            request.setAttribute("student", student);
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateStudent.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "无法查询到此学生，请联系管理员");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.do");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
