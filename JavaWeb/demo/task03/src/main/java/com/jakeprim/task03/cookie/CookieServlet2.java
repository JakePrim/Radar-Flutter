package com.jakeprim.task03.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CookieServlet2", urlPatterns = "/cookie2")
public class CookieServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 创建cookie信息
        Cookie cookie = new Cookie("name", "liubei");
        //2. 获取cookie默认使用期限
        int maxAge = cookie.getMaxAge();
        System.out.println("cookie默认使用期限:" + maxAge);
        //3. 修改cookie信息的使用期限
        //正数：保存固定的秒数   0：立即删除   负数：表示浏览器关闭后失效
        cookie.setMaxAge(60);
        //4. 添加cookie
        response.addCookie(cookie);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
