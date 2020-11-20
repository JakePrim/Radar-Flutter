package com.prim.global;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 核心控制器
 * 将多个Servlet整合为一个Servlet
 *
 * @author prim
 */
public class CoreController extends GenericServlet {

    private static final String TAG = "CoreController";

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        // 定义请求URL规则
        // /login.do DefaultController -> login()
        // /Cake/list.do CakeController -> list()
        // /admin/Cake/add.do CakeController -> add()
        //如上通过反射请求相应类的方法
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        System.out.println("path = " + path);
        String adminPath = "/admin";
        //解析路径
        if (path.contains(adminPath)) {
            path = path.substring(7);
        } else {
            path = path.substring(1);
        }
        //解析后的路径
        /**
         * login.do
         * Cake/list.do
         * Cake/add.do
         */
        //判断是否有相应的模块
        String separate = "/";
        int index = path.indexOf(separate);
        String className = null;
        String methodName = null;
        if (index != -1) {
            className = "com.prim.controller." + path.substring(0, index) + "Controller";
            methodName = path.substring(index + 1, path.indexOf(".do"));
        } else {
            className = "com.prim.controller.DefaultController";
            methodName = path.substring(0, path.indexOf(".do"));
        }
        //反射调用相应的方法
        try {
            System.out.println(TAG + " -> className:" + className + " methodName:" + methodName);
            Class<?> aClass = Class.forName(className);
            Object controllerObj = aClass.newInstance();
            Method method = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(controllerObj, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
