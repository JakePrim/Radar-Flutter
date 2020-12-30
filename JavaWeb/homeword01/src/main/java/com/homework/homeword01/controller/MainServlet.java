package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.PageHelp;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = "/main.do")
public class MainServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p = request.getParameter("page");
        String size = request.getParameter("size");
        int curPage = 1;
        int pageSize = 0;
        if (null == p || "".equals(p)) {
            curPage = 1;
        } else {
            curPage = Integer.parseInt(p);
        }
        if (null == size || "".equals(size)) {
            pageSize = 5;
        } else {
            pageSize = Integer.parseInt(size);
        }
        StudentService service = new StudentService();
        PageHelp<Student> page = service.findPage(curPage, pageSize);
        System.out.println("curPage："+page.getCurrentPage()+" startIndex:"+page.getStartIndex()
                +" nextIndex:"+page.getNextIndex());
        request.setAttribute("page", page);
        //转发给main.jsp 进行显示
        RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
