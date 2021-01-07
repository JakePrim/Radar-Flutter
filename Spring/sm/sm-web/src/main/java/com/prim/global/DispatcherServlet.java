package com.prim.global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author prim
 */
public class DispatcherServlet extends GenericServlet {
    private ApplicationContext applicationContext;

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * 定义命名规则
         * /staff/add.do
         * staffController
         * add(request,response);
         */
        // 得到：staff/add.do
        String path = request.getServletPath().substring(1);
        String beanName = null;
        String methodName = null;
        //判断中间是否存在分隔符
        int index = path.indexOf("/");
        if (index != -1) {
            beanName = path.substring(0, index) + "Controller";
            methodName = path.substring(index + 1, path.indexOf(".do"));
        } else {
            beanName = "selfController";
            methodName = path.substring(0, path.indexOf(".do"));
        }
        //从Spring IOC中配置的 @controller bean中获取
        Object obj = applicationContext.getBean(beanName);
        try {
            Method method = obj.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(obj, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
