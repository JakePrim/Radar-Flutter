package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.Admin;
import com.homework.homeword01.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 管理员登录
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");
        Admin loginAdmin = (Admin) session.getAttribute("login");
        if (loginAdmin != null) {
            response.sendRedirect("main");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            AdminService adminService = new AdminService();
            Admin admin = adminService.adminLogin(new Admin(username, password));
            if (admin != null) {
                session.setAttribute("login", admin);
                //注意重定向到某个Servlet需要指定工程名
                response.sendRedirect(getServletContext().getContextPath()+"/main");
            } else {
                request.setAttribute("error", "用户名或密码错误");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
