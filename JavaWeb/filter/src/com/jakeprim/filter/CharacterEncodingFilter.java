package com.jakeprim.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 */
@WebFilter(filterName = "/character",urlPatterns = "/*",initParams = {
		@WebInitParam(name = "encoding",value = "UTF-8"),
		@WebInitParam(name = "p1",value = "v1"),
		@WebInitParam(name = "p2",value = "v2")
})
public class CharacterEncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharacterEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//注意这里要强转一下
		//解决post请求的中文乱码问题
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		servletRequest.setCharacterEncoding(encoding);//修改 如 GBK
		//解决响应中的中文乱码问题
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		servletResponse.setContentType("text/html;charset="+encoding);//修改 如 GBK,这样不好控制 比较麻烦,  将可变的选项进行参数化设置
		

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		encoding = fConfig.getInitParameter("encoding");//读取配置的参数
		System.out.println(encoding);
		String p1 = fConfig.getInitParameter("p1");
		String p2 = fConfig.getInitParameter("p2");
		System.out.println(p1+":"+p2);
	}
	
	private String encoding;

}
