package com.jakeprim.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ContentTypeServlet
 */
@WebServlet("/ct")
public class ContentTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");//html
//		response.setContentType("text/plain");//纯文本
//		response.setContentType("text/xml");//xml显示
		response.setContentType("application/x-msdownload");//下载的形式显示
		response.getWriter().println("<h1><a href='http://www.baidu.com'><span>Google</span></a></h1>");
	}

}
