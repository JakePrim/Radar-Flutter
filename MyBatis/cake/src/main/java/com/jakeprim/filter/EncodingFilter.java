package com.jakeprim.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 编码过滤器
 * @author prim
 */
public class EncodingFilter implements Filter {
    private String encoding = "utf-8";

    public void init(FilterConfig filterConfig) throws ServletException {
        String paramName = "encoding";
        if (filterConfig.getInitParameter(paramName) != null){
            encoding = filterConfig.getInitParameter(paramName);
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
