package com.jakeprim.demo02.servlet;

import com.jakeprim.demo02.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 解决中文乱码问题
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        //2. 获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("获取到的用户名：" + username + " 获取到的密码:" + password);
        //3. 处理参数 操作DB
        // 打包成对象Javabean
        User user = new User(username,password);
        // 将Javabean交给dao层处理


        //4. 将响应结果返回给客户端
        PrintWriter writer = response.getWriter();
        writer.write("注册成功");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
