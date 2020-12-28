package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchContent = request.getParameter("searchContent");
        StudentService service = new StudentService();
        List<Student> students;
        if (null != searchContent && !"".equals(searchContent)) {
            Student student = service.findById(Integer.parseInt(searchContent));
            if (student == null) {
                //如果什么都没有输入则查询全部
                request.setAttribute("error", "没有查询到此学生的任何信息!");
                //将查询的信息转发到main.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("main");
                dispatcher.forward(request, response);
                return;
            } else {
                students = new ArrayList<>();
                students.add(student);
            }
        } else {
            //如果什么都没有输入则查询全部
            students = service.findAll();
        }
        request.setAttribute("students", students);
        //将查询的信息转发到main.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
