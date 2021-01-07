package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.service.SClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddTransitServlet", urlPatterns = "/addTransit.do")
public class AddTransitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SClassService sClassService = new SClassService();
        List<SClass> classes = sClassService.findAll();
        // 将所有的班级信息添加到请求参数中
        request.setAttribute("classes", classes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("addStudent.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
