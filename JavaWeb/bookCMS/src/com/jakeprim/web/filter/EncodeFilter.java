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

/**
 * 处理中文乱码的过滤器
 * 
 * @author prim
 */
public class EncodeFilter implements Filter {

	private String encoding;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("乱码过滤器");
		// 解决post请求的中文乱码问题
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		servletRequest.setCharacterEncoding(encoding);// 修改 如 GBK
		// 解决响应中的中文乱码问题
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		servletResponse.setContentType("text/html;charset=" + encoding);// 修改 如 GBK,这样不好控制 比较麻烦, 将可变的选项进行参数化设置

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");
	}

}
