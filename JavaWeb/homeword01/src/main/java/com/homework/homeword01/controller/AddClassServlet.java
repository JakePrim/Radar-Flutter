package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.service.SClassService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddClassServlet",urlPatterns = "/addclass.do")
public class AddClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SClassService sClassService = new SClassService();
        String name = request.getParameter("name");
        String grade = request.getParameter("grade");
        String teacher = request.getParameter("teacher");
        String slogan = request.getParameter("slogan");
        SClass sClass = new SClass(name, grade, teacher, slogan, 0);
        int update = sClassService.add(sClass);
        if (update > 0){
            response.sendRedirect(getServletContext().getContextPath()+"/classmain.do");
        }else {
            request.setAttribute("error","添加失败，请重试");
            request.getRequestDispatcher("addClass.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
