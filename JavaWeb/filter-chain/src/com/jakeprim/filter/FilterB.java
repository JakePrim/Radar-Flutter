package com.jakeprim.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebFilter(filterName = "FilterB",urlPatterns = "/*")
public class FilterB implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String trigetUri = httpServletRequest.getRequestURI();// /index.html
		System.out.println("trigetUri:" + trigetUri + httpServletRequest.getContextPath());
		if (trigetUri.indexOf("/desktop/index.html") != -1 || trigetUri.indexOf("/mobile/index.html") != -1) {
			chain.doFilter(request, response);
		} else if (trigetUri.indexOf("/index.html") != -1) {
			String userAgent = httpServletRequest.getHeader("User-Agent").toLowerCase();
			if (userAgent.indexOf("iphone") != -1 || userAgent.indexOf("android") != -1) {
				trigetUri = httpServletRequest.getContextPath() + "/mobile/index.html";
				httpServletResponse.sendRedirect(trigetUri);
			} else {
				trigetUri = httpServletRequest.getContextPath() + "/desktop/index.html";
				httpServletResponse.sendRedirect(trigetUri);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
