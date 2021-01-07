package com.jakeprim.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 2,urlPatterns = "/analys")
public class AnalysServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4753299304382669483L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("正在分析数据");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("销毁分析数据的servlet");
	}
}
