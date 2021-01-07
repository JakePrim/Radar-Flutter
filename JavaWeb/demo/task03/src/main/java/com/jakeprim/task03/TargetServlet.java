package com.jakeprim.task03;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TargetServlet", urlPatterns = "/target")
public class TargetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("转发过来了...");
        Object value = request.getAttribute("key");
        System.out.println("获取转发过来的请求数据：" + value);//value
        response.setContentType("test/html;charset=utf-8");
        response.getWriter().write("<h1>转发成功</h1>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
