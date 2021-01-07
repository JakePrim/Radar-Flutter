package com.jakeprim.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*",filterName = "sample2")
public class FilterSample2 implements Filter {

	@Override
	public void destroy() {
		System.out.println("过滤器被销毁2");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("过滤器已生效2");
		//传递到下一个过滤链
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("过滤器初始化2");
	}

}
