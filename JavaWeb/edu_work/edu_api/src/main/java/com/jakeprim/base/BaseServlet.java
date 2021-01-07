package com.jakeprim.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    /**
     * doGet 方法作为一个调度器使用，根据请求的功能不同调用对应的方法
     * 必须传递一个参数:methodName = 功能名
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("methodName");
        if (methodName != null) {
            try {
                //通过返回优化方法的调用
                Class c = this.getClass();
                //根据传入的方法名获取对应的方法对象
                Method method = c.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                //执行方法
                method.invoke(this, request, response);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.out.println("请求的功能不存在");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
