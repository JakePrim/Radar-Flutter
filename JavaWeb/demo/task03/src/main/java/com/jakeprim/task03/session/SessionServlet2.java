package com.jakeprim.task03.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SessionServlet2", urlPatterns = "/session2")
public class SessionServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 设置属性名和属性值
        HttpSession session = request.getSession();
        session.setAttribute("name", "马超");
        //2. 获取指定属性名对应的属性值
        System.out.println(session.getAttribute("name"));
        session.removeAttribute("name");
        System.out.println(session.getAttribute("name"));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
