package com.jakeprim.demo02.filter;

import javax.servlet.*;
import java.io.IOException;

public class BFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("这是第二道防线");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("第二道防线返回");
    }

    @Override
    public void destroy() {

    }
}