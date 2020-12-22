package com.jakeprim.demo02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String characterEncoding = request.getCharacterEncoding();
        System.out.println("获取请求的默认编码方式:"+characterEncoding);//null 是以浏览器的默认编码方式发送信息
        //修改编码的方式为 UTF-8
        request.setCharacterEncoding("UTF-8");

        //接收请求
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            //name:[çç»´]   请求信息中文乱码问题
            System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }

        //获取响应数据的编码方式
        String encoding = response.getCharacterEncoding();
        System.out.println("获取响应的默认编码方式:" + encoding);//获取响应的默认编码方式:ISO-8859-1
        //修改编码方式为UTF-8 要在发送数据之前进行设置 要在getWriter()方法之前设置 否则无效
        response.setContentType("text/html;charset=UTF-8");

        //向客户的发送响应数据
        PrintWriter writer = response.getWriter();
//        writer.write("I Received! 发送中文的问题");
        Random random = new Random();
        int i = random.nextInt(100) + 1;
        writer.write("<h1>" + i + "</h1>");
        System.out.println("服务器发送成功");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
