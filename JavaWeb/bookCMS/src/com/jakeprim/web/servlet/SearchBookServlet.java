package com.jakeprim.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.jakeprim.domain.Book;
import com.jakeprim.service.BookService;
import com.jakeprim.service.impl.BookServiceImpl;

/**
 * Servlet implementation class SearchBookServlet
 */
@WebServlet("/searchbook.do")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchContent = request.getParameter("searchContent");
		System.out.println("searchContent:"+searchContent);
		BookService bookService = new BookServiceImpl();
		List<Book> books = bookService.getBooksByCategory(searchContent);
		//转换为json字符串
		String jsonString = JSON.toJSONString(books);
		response.getWriter().append(jsonString);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
