package com.jakeprim.demo02.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 同级在线人数
 */
public class OnlineUser implements HttpSessionListener, ServletContextListener {
    //全局的对象记录在线人数
    ServletContext servletContext = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        servletContext = sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        servletContext = null;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        //每个用户都会对应着一个会话，监听会话创建可以同级在线数
        System.out.println("新用户上线了...");
        //判断是否为第一个用户
        Object count = servletContext.getAttribute("count");
        if (null == count) {
            servletContext.setAttribute("count", 1);
        } else {
            //不是第一个用户 则数量加一
            Integer c = (Integer) count;
            c++;
            servletContext.setAttribute("count", c);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        //用户退出了 下线了
        Object count = servletContext.getAttribute("count");
        if (null != count) {
            Integer c = (Integer) count;
            c--;
            servletContext.setAttribute("count", c);
        }
    }
}
