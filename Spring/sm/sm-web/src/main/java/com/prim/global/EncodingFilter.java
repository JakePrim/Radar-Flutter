package com.prim.global;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author prim
 * 编码过滤器
 */
public class EncodingFilter implements Filter {
    private String encoding = "utf-8";

    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("ENCODING");
        if (encoding != null) {
            this.encoding = filterConfig.getInitParameter("ENCODING");
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(encoding);
        servletResponse.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
