package com.jakeprim.task01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet 只会创建和初始化一次
 * 构造方法
 * 初始化操作
 * 这是采用HTTPServlet 推荐使用
 *
 * 以后在访问该Servlet就不会调用 构造方法和初始化方法，而是会调用service方法
 *
 * 停止服务器才会调用 销毁方法
 */
public class TestServlet3 extends HttpServlet {
    public TestServlet3() {
        System.out.println("构造方法");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("初始化操作");
    }

    @Override
    public void destroy() {
        super.destroy();
        System.out.println("销毁操作");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("这是采用HTTPServlet 推荐使用");
    }
}
