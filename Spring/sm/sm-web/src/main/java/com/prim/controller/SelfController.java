package com.prim.controller;

import com.prim.pojo.Staff;
import com.prim.service.SelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller("selfController")
public class SelfController {

    @Autowired
    private SelfService selfService;

    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/login.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        Staff login = selfService.login(account, password);
        if (login != null) {
            request.getSession().setAttribute("user", login);
            response.sendRedirect("main.do");
        } else {
            response.sendRedirect("toLogin.do");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("toLogin.do");
    }

    public void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/index.jsp").forward(request, response);
    }

    public void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/info.jsp").forward(request, response);
    }

    public void toChangePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/change_password.jsp").forward(request, response);
    }

    public void changePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取当前的用户
        Staff staff = (Staff) request.getSession().getAttribute("user");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if (staff.getPassword().equals(password)) {
            selfService.changePassword(staff.getId(), password1);
            response.getWriter().print("<script type=\"text/javascript\">parent.location.href=\"../logout.do\"</script>");
        } else {
            response.sendRedirect("toChangePassword.do");
        }
    }
}
