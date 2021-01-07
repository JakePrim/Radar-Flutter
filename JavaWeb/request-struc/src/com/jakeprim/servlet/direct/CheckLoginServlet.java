package com.jakeprim.servlet.direct;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckLoginServlet
 */
@WebServlet("/direct/check")
public class CheckLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("用户登录校验成功,跳转首页");
		//将请求和响应同时传递到下一个Servlet中
		request.setAttribute("name", "JakePrim");
		//将request和response传递到了下一个Servlet类中
//		request.getRequestDispatcher("/direct/index").forward(request, response);
		response.sendRedirect("/request-struc/direct/index");
	}

}
