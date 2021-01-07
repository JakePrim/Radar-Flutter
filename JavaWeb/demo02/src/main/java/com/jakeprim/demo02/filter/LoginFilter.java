package com.jakeprim.demo02.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 拦截器 判断用户是否登录
 */
public class LoginFilter implements Filter {

    public LoginFilter() {
        System.out.println("LoginFilter 构造方法");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoginFilter 初始化操作.....");
        System.out.println("过滤器的名称："+filterConfig.getFilterName());
        Enumeration<String> parameterNames = filterConfig.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            System.out.println(name+" : "+filterConfig.getInitParameter(name));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("LoginFilter 拦截访问请求.....");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        String servletPath = request.getServletPath();
        //判断用户信息是否存在session中
        if (username == null){
            //拦截
            request.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
        }else {
            //处理完逻辑，必须调用下面的方法 放行的意思
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        System.out.println("LoginFilter 销毁.....");
    }
}
