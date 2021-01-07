package com.jakeprim.web.servlet;

import com.jakeprim.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 一个servlet对应一个模块
 */
@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends BaseServlet {

    public void addCourse(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("添加课程");
    }

    public void findByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询课程");
    }
}
