package com.jakeprim.task03.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieServlet3", urlPatterns = "/cookie3")
public class CookieServlet3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 创建cookie指定数值
        Cookie cookie = new Cookie("n", "zhaoyun");
        // 修改cookie的路径信息 只有访问该路径或者子路径才会发送cookie信息
        cookie.setPath(request.getContextPath() + "/hello");
        //2. 添加到响应信息
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
