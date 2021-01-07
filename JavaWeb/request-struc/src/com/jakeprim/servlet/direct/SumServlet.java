package com.jakeprim.servlet.direct;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SumServlet
 */
@WebServlet("/direct/sum")
public class SumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SumServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String num = request.getParameter("num");
		if (num != null) {
			try {
				Integer number = Integer.valueOf(num);
				if (number > 100 || number <= 0) {
					request.setAttribute("msg", "输入数值必须>0 and <=100");
					request.getRequestDispatcher("/direct/error").forward(request, response);
					return;
				}
				Integer sum = 0;
				for (int i = 1; i <= number; i++) {
					sum += i;
				}
				request.setAttribute("sum", sum);
				request.getRequestDispatcher("/direct/result").forward(request, response);
			} catch (Exception e) {
				request.setAttribute("msg", "输入数值类型错误");
				request.getRequestDispatcher("/direct/error").forward(request, response);
			}
		} else {
			request.setAttribute("msg", "输入为null!");
			request.getRequestDispatcher("/direct/error").forward(request, response);
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
