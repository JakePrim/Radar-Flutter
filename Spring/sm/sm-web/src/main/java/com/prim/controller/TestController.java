package com.prim.controller;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author prim
 * 测试核心控制器
 */
@Controller("testController")
public class TestController {
    /**
     * /test/show.do
     *
     * @param request
     * @param response
     */
    public void show(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("name", "show!");
        request.getRequestDispatcher(request.getContextPath() + "/show.jsp").forward(request, response);
    }
}
