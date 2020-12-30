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

@WebServlet(name = "UpdateClassServlet",urlPatterns = "/updateclass.do")
public class UpdateClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String name = request.getParameter("name");
        String grade = request.getParameter("grade");
        String teacher = request.getParameter("teacher");
        String slogan = request.getParameter("slogan");
        String num = request.getParameter("num");
        SClass sClass = new SClass(name, grade, teacher, slogan, Integer.parseInt(num));
        sClass.setId(Integer.parseInt(cid));
        SClassService service = new SClassService();
        int update = service.update(sClass);
        if (update > 0) {
            //更新成功 重定向到main
            response.sendRedirect(getServletContext().getContextPath() + "/classmain.do");
        } else {
            request.setAttribute("error", "更新失败，请联系管理员");
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateClass.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
