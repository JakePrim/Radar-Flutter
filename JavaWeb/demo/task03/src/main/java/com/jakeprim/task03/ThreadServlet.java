package com.jakeprim.task03;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThreadServlet", urlPatterns = "/thread")
public class ThreadServlet extends HttpServlet {
    private String name;//准备一个成员变量，作为多个线程共享资源

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //不推荐使用锁的方式
//        synchronized (this) {
//            //1. 获取request对象中名字为name的参数数值并赋值给成员变量name
//            name = request.getParameter("name");
//            System.out.println("获取到的name:" + name);
//            //2. 睡眠5s
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            //3. 将name发送给浏览器
//            PrintWriter writer = response.getWriter();
//            writer.write("<h1>" + name + "</h1>");
//            writer.close();
//        }

        //推荐：使用局部变量的方式
        //1. 获取request对象中名字为name的参数数值并赋值给成员变量name
        String name = request.getParameter("name");
        System.out.println("获取到的name:" + name);
        //2. 睡眠5s
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //3. 将name发送给浏览器
        PrintWriter writer = response.getWriter();
        writer.write("<h1>" + name + "</h1>");
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
