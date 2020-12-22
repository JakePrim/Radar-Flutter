package com.jakeprim.demo02;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ActionServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("初始化操作......");
        //Servlet的别名是:actionServlet
        System.out.println("Servlet的别名是:" + config.getServletName());
        //获取初始化参数name:jakeprim
        System.out.println("获取初始化参数name:" + config.getInitParameter("name"));
        System.out.println("获取初始化参数config:" + config.getInitParameter("config"));
        //获取配置信息
        Enumeration<String> names = config.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            System.out.println(name + ":" + config.getInitParameter(name));
        }

        //获取servletContext接口的引用
        ServletContext servletContext = config.getServletContext();
        System.out.println("获取到的servletContext:" + servletContext);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求和响应
    }
}
