package com.jakeprim.demo02;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ContextServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取ServletContext参数配置
        ServletContext servletContext = this.getServletContext();
//        ServletContext servletContext = getServletConfig().getServletContext();
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            System.out.println(name + ":" + servletContext.getInitParameter(name));
        }
        //2. 相关路径的获取
        String path = servletContext.getContextPath();
        //本质上是获取工程路径信息 /工程名
        System.out.println("获取上下文关联的路径信息:" + path);//   /demo02
        //   / 被解析为 http://ip地址：端口号/工程名   工程名
        //获取到的是部署工程路径信息
        String realPath = servletContext.getRealPath("/");
        // 获取实际路径信息:/Users/prim/workspace/java/Superman/JavaWeb/demo02/target/demo02-1.0-SNAPSHOT/
        System.out.println("获取实际路径信息:" + realPath);

        //3. 设置和获取属性信息
        servletContext.setAttribute("key", "value");
        Object key = servletContext.getAttribute("key");
        System.out.println("获取设置的属性值:" + key);//value
        servletContext.removeAttribute("key");
        Object key1 = servletContext.getAttribute("key");
        System.out.println("获取删除设置的属性值:" + key1);//null
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
