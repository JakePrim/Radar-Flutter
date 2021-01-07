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

@WebServlet(name = "UpdateServlet", urlPatterns = "/update.do")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("num");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String date = request.getParameter("date");
        String email = request.getParameter("email");
        String desc = request.getParameter("desc");
        String aClass = request.getParameter("class");
        Student student = new Student(name, Integer.parseInt(sex), date, desc, email, Integer.parseInt(aClass));
        student.setId(Integer.parseInt(id));
        StudentService service = new StudentService();
        int update = service.update(student);
        if (update > 0) {
            //更新成功 重定向到main
            response.sendRedirect(getServletContext().getContextPath() + "/main.do");
        } else {
            request.setAttribute("error", "更新失败，请联系管理员");
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateStudent.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
