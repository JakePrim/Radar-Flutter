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

@WebServlet(name = "AddServlet", urlPatterns = "/add")
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String date = request.getParameter("date");
        String sex = request.getParameter("sex");
        String email = request.getParameter("email");
        String desc = request.getParameter("desc");
        System.out.println(name + ":" + date + ":" + sex + ":" + email + ":" + desc);
        StudentService service = new StudentService();
        int row = service.add(new Student(name, Integer.parseInt(sex), date, desc, email));
        if (row > 0) {
            //添加成功，重定向到/main 重新请求数据库显示数据
            response.sendRedirect(getServletContext().getContextPath() + "/main");
        } else {
            //添加失败 转发给当前页面显示失败信息
            request.setAttribute("error", "添加信息失败，请重试！");
            RequestDispatcher dispatcher = request.getRequestDispatcher("addStudent.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
