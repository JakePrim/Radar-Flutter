package com.jakeprim.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WorkTestServlet
 */
@WebServlet("/work/test")
public class WorkTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HashMap<String, String> map;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkTestServlet() {
		super();
		map = new HashMap<String, String>();
		map.put("apple", "苹果");
		map.put("mac", "苹果笔记本电脑");
		map.put("window", "Windows电脑");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getParameter("key");// 获取请求参数
		if (map.containsKey(key)) {
			request.setAttribute("key", map.get(key));
			//请求转发的方式
			System.out.println(map.get(key));
            request.getRequestDispatcher("/success.jsp").forward(request, response);//请求转发不需要 context-path
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("msg", "没有找到对应的单词解释");
			response.sendRedirect("/request-struc/error.jsp");//注意重定向需要 context-path
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
