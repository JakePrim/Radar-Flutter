package com.jakeprim.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1,urlPatterns = "/import")
public class ImportServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7211302715512182770L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("正在导入数据");
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("销毁导入数据的servlet");
	}
}
