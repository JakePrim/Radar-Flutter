package com.homework.homeword01.controller;

import com.homework.homeword01.service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = "/delete.do")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (null != id && !"".equals(id)) {
            StudentService service = new StudentService();
            int delete = service.delete(Integer.parseInt(id));
            if (delete > 0) {
                //删除成功 重定向到main 重新请求数据
                response.sendRedirect(getServletContext().getContextPath() + "/main.do");
            } else {
                //删除失败
                request.setAttribute("error", "删除失败，请联系管理员");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.do");
                requestDispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "删除id 失败，请联系管理员");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("main.do");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
