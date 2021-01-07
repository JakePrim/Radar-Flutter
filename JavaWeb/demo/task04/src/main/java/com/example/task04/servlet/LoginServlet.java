package com.example.task04.servlet;

import com.example.task04.dao.UserDaoImp;
import com.example.task04.model.User;
import com.example.task04.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(name + ":" + password);
        //创建UserService对象
        UserService userService = new UserService();
        User user = userService.userLoginService(new User(name, password));
        if (user != null) {
            System.out.println("登录成功");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("main.jsp");
        } else {
            System.out.println("登录失败。用户名或密码错误");
            request.setAttribute("error", "用户名或密码错误");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
