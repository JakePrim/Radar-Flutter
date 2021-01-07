package com.jakeprim.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 启动Servlet
 * @author prim
 *
 */
@WebServlet(loadOnStartup = 0,urlPatterns = "/create")
public class CreateServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2001453607694670884L;

	@Override
    public void init(ServletConfig config) throws ServletException {
    	System.out.println("正在创建数据库保存");
    }
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println("销毁创建数据库的servlet");
	}
}
