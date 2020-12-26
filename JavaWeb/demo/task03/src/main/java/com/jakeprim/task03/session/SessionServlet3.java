package com.jakeprim.task03.session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SessionServlet3", urlPatterns = "/session3")
public class SessionServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取session对象
        HttpSession session = request.getSession();
        //2. 获取对象的默认失效实现
        int maxInactiveInterval = session.getMaxInactiveInterval();
        System.out.println("失效时间:" + maxInactiveInterval);//失效时间:1800  web.xml 失效时间:600
        //3. 修改失效时间
        session.setMaxInactiveInterval(1200);
        int maxInactiveInterval2 = session.getMaxInactiveInterval();
        System.out.println("修改后的失效时间:" + maxInactiveInterval2);//修改后的失效时间:1200
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
