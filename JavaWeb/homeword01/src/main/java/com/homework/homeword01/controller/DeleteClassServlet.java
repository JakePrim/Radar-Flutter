package com.homework.homeword01.controller;

import com.homework.homeword01.service.SClassService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteClassServlet",urlPatterns = "/deleteclass.do")
public class DeleteClassServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (null != id && !"".equals(id)){
            SClassService classService = new SClassService();
            int delete = classService.delete(Integer.parseInt(id));
            if (delete > 0){
                response.sendRedirect(getServletContext().getContextPath()+"/classmain.do");
            }else {
                request.setAttribute("error", "删除失败，请联系管理员");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("classmain.do");
                requestDispatcher.forward(request, response);
            }
        }else {
            request.setAttribute("error", "删除失败，请联系管理员");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("classmain.do");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
