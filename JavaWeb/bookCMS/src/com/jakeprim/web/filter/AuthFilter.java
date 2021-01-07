package com.jakeprim.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jakeprim.domain.User;

/**
 * 验证登录的过滤器
 * 
 * @author prim
 *
 */
public class AuthFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		System.out.println("验证登录过滤器"+servletRequest.getRequestURI());
		User user = (User) servletRequest.getSession().getAttribute("loginUser");
		if (user != null) {
			chain.doFilter(request, response);
		} else {
			servletResponse.sendRedirect(servletRequest.getContextPath()+"/login.jsp");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
