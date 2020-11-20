package com.jakeprim.servlet.cookie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexCookieServlet
 */
@WebServlet("/cookie/index")
public class IndexCookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexCookieServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取cookie信息
		Cookie[] cookies = request.getCookies();
		//打印cookies信息 注意如果没有cookie则getCookies返回为null
		if (cookies == null) {
			response.getWriter().println("User Logout");
		}else {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user")) {
					response.getWriter().println("User Login "+cookie.getName()+":"+cookie.getValue());
					break;
				}else {
					response.getWriter().println("User Logout");
					break;
				}
			}
		}
	}

}
