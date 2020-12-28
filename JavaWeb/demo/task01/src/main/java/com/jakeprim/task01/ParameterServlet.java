package com.jakeprim.task01;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "ParameterServlet", urlPatterns = "/parameter")
public class ParameterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取指定参数名称对应的参数值并打印
        String name = request.getParameter("name");
        System.out.println("姓名:" + name);
        String[] hobbies = request.getParameterValues("hobby");
        System.out.println("爱好：" + Arrays.toString(hobbies));
        //2. 获取所有参数的名称
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            System.out.println(names.nextElement() + " ");
        }
        System.out.println();
        //3. 获取请求参数的键值对
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entrySet) {
            System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
        //4. 获取客户端请求的其他信息
        /**
         * IP:0:0:0:0:0:0:0:1
         * 端口号:55431
         * 资源路径:/task01/parameter
         * 完整的资源路径为：http://localhost:8080/task01/parameter
         * 请求方式：POST
         * 请求的附带参数为:null
         * 请求的Servlet路径为:/parameter
         */
        System.out.println("IP:"+request.getRemoteAddr());
        System.out.println("端口号:"+request.getRemotePort());
        System.out.println("资源路径:"+request.getRequestURI());
        System.out.println("完整的资源路径为："+request.getRequestURL());
        System.out.println("请求方式："+request.getMethod());
        System.out.println("请求的附带参数为:"+request.getQueryString());
        System.out.println("请求的Servlet路径为:"+request.getServletPath());


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
