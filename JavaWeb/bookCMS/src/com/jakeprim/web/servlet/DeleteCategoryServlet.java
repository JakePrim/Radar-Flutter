package com.jakeprim.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jakeprim.service.CategoryService;
import com.jakeprim.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class DeleteCategoryServlet
 */
@WebServlet("/deleteCategory")
public class DeleteCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteCategoryServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		System.out.println("删除分类:"+categoryId);
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.deleteCatgory(categoryId);
		request.getServletContext().setAttribute("category", CategoryService.categoryDb);
		response.sendRedirect(request.getContextPath()+"/categoryList.jsp");
	}
}
