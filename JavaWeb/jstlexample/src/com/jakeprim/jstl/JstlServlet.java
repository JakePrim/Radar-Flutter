package com.jakeprim.jstl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.ls.LSException;

/**
 * Servlet implementation class JstlServlet
 */
@WebServlet("/jstl")
public class JstlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JstlServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("score", 58);
		request.setAttribute("grade", "E");
		List<Company> list = new ArrayList<Company>();
		list.add(new Company("腾讯", "www.qq.com"));
		list.add(new Company("百度", "www.百度.com"));
		list.add(new Company("JakePrim", "www.jakeprim.com"));
		request.setAttribute("companies", list);
		
		for (Company company : list) {
			
		}

		request.getRequestDispatcher("/foreach.jsp").forward(request, response);
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
