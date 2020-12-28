package com.jakeprim.task03.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "CookieServlet", urlPatterns = "/cookie")
public class CookieServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                System.out.println("获取到的cookies：" + cookie.getName() + ":" + cookie.getValue());
                if ("name".equalsIgnoreCase(cookie.getName())) {
                    cookie.setValue("guanyu");
                    response.addCookie(cookie);
                }
            }
        }

//        System.out.println("接受到请求");
//        Cookie cookie = new Cookie("name", "张飞");
//        response.addCookie(cookie);
//        System.out.println("创建cookie成功");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
