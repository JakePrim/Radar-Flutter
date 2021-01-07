package com.prim.controller;

import com.prim.pojo.Log;
import com.prim.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("logController")
public class LogController {
    @Autowired
    private LogService logService;

    public void operationLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getOperationLog();
        request.setAttribute("list", list);
        request.setAttribute("type", "operation");
        request.getRequestDispatcher(request.getContextPath() + "/log_list.jsp").forward(request, response);
    }

    public void systemLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getSystemLog();
        request.setAttribute("list", list);
        request.setAttribute("type", "system");
        request.getRequestDispatcher(request.getContextPath() + "/log_list.jsp").forward(request, response);
    }

    public void loginLog(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Log> list = logService.getLoginLog();
        request.setAttribute("list", list);
        request.setAttribute("type", "login");
        request.getRequestDispatcher(request.getContextPath() + "/log_list.jsp/").forward(request, response);
    }
}
