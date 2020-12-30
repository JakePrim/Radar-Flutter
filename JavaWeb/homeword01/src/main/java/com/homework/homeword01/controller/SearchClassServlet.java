package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.SClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchClassServlet", urlPatterns = "/searchclass.do")
public class SearchClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String classType = request.getParameter("classType");
            String searchContent = request.getParameter("searchContent");
            SClassService service = new SClassService();
            System.out.println("classType:" + classType + " searchContent:" + searchContent);
            switch (classType) {
                case "cid":
                    SClass sClass = service.findById(Integer.parseInt(searchContent));
                    System.out.println("得到的结果:" + sClass);
                    extracted(request, response, sClass);
                    break;
                case "name":
                    extracted(request, response, service.findByName(searchContent));
                    break;
                case "grade":
                    extracted(request, response, service.findByGrade(searchContent));
                    break;
                case "teacher":
                    extracted(request, response, service.findByTeacher(searchContent));
                    break;
                default:
                    System.out.println("走默认的default");
                    request.setAttribute("error", "查询失败");
                    request.getRequestDispatcher("classmain.do").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            System.out.println("出现错误：" + e.getMessage());
            request.setAttribute("error", "查询失败");
            request.getRequestDispatcher("classmain.do").forward(request, response);
        }
    }

    private void extracted(HttpServletRequest request, HttpServletResponse response, SClass sClass) throws ServletException, IOException {
        List<SClass> classes = new ArrayList<>();
        classes.add(sClass);
        request.setAttribute("classes", classes);
        //将查询的信息转发到main.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("mainClass.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
