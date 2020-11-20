package com.jakeprom.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//添加属性
		req.getServletContext().setAttribute("sc-attr1", "sc-attr1-value1");
		req.getSession().setAttribute("session-attr1", "session-attr1-value1");
		req.setAttribute("request-attr1", "request-attr1-value1");
		
		//更新属性
//		req.getServletContext().setAttribute("sc-attr1", "sc-attr1-value2");
//		req.getSession().setAttribute("session-attr1", "session-attr1-value2");
//		req.setAttribute("request-attr1", "request-attr1-value2");
//		
//		//移除属性
//		req.getServletContext().removeAttribute("sc-attr1");
//		req.getSession().removeAttribute("session-attr1");
//		req.removeAttribute("request-attr1");
		
	}
}
