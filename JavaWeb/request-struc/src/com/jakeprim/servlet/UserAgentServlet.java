package com.jakeprim.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserAgentServlet
 */
@WebServlet("/ua")
public class UserAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAgentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String uaString = request.getHeader("User-Agents");
		if (uaString.length()>0) {
			response.getWriter().println("1");
		}
		response.getWriter().println(uaString);
		String out = "";
		if (uaString.indexOf("Window") != -1 || uaString.indexOf("Macintosh") != -1) {
			out = "<h1>电脑端</h1>";
		}else if (uaString.indexOf("Android") !=-1 || uaString.indexOf("iPhone")!=-1) {
			out = "<h1>手机端</h1>";
		}
		response.getWriter().println(out);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
