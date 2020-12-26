package com.jakeprim.task02.servlet;

import com.jakeprim.task02.dao.UserDao;
import com.jakeprim.task02.model.User;

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
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        //1. 获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + ":" + password);
        User user = new User(username, password);
        //2. 创建DAO操作DB
        UserDao userDao = new UserDao();
        int row = userDao.createUser(user);
        PrintWriter writer = response.getWriter();
        if (row > 0) {
            writer.write("注册成功");
        }else {
            writer.write("注册失败");
        }
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
