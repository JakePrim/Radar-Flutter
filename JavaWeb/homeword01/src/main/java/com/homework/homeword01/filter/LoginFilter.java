package com.homework.homeword01.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录信息过滤 拦截该项目的所有URL
 */
@WebFilter(filterName = "LoginFilter",urlPatterns = "/*" )
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //统一解决请求的中文乱码和响应中文乱码问题
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String contextPath = request.getContextPath();
        String requestURI = request.getRequestURI();
        System.out.println("requestURI："+requestURI+" contextPath："+contextPath);
        if (requestURI.contains("login")){
            //跳转到登录页面不进行拦截
            chain.doFilter(req, resp);
        }else {
            Cookie[] cookies = request.getCookies();
            boolean isLogin = false;
            for (Cookie cookie : cookies) {
                if ("isLogin".equalsIgnoreCase(cookie.getName())){
                    String name = cookie.getValue();
                    //查询数据库中是否存在该用户
                    isLogin = true;
                }
            }
            if (isLogin){
                chain.doFilter(req, resp);
            }else {
                response.sendRedirect("login.jsp");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
