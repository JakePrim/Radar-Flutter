package com.prim.global;

import com.prim.pojo.Staff;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录过滤器
 */
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        if (path.toLowerCase().contains("login")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Staff staff = (Staff) request.getSession().getAttribute("user");
            if (staff == null) {
                response.sendRedirect(request.getContextPath() + "/toLogin.do");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    public void destroy() {

    }
}
