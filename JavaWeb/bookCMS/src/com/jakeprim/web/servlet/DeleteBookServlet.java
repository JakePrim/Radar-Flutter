package com.jakeprim.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jakeprim.service.BookService;
import com.jakeprim.service.impl.BookServiceImpl;


@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DeleteBookServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookId = request.getParameter("bookId");
		BookService bookService = new BookServiceImpl();
		bookService.deteleBook(bookId);
		request.setAttribute("books", BookService.bookDb);
		response.sendRedirect(request.getContextPath()+"/bookList.jsp");
	}
}
