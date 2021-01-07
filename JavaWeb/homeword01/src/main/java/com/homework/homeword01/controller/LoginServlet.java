package com.homework.homeword01.controller;

import com.homework.homeword01.pojo.Admin;
import com.homework.homeword01.service.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 管理员登录
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Admin loginAdmin = (Admin) session.getAttribute("login");
        if (loginAdmin != null) {
            response.sendRedirect("main");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            AdminService adminService = new AdminService();
            Admin admin = adminService.adminLogin(new Admin(username, password));
            if (admin != null) {
                //存储到cookie中
                Cookie cookie = new Cookie("isLogin", username);
                cookie.setMaxAge(604800);//一周免登陆
                cookie.setPath(request.getContextPath());//只有访问该项目的URL才会传递cookie
                response.addCookie(cookie);
                //注意重定向到某个Servlet需要指定工程名
                response.sendRedirect(getServletContext().getContextPath()+"/main.do");
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
