package com.prim.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prim.domain.Category;
import com.prim.domain.PageBean;
import com.prim.domain.Product;
import com.prim.service.CategoryService;
import com.prim.service.ProductService;
import com.prim.service.impl.CategoryServiceImpl;
import com.prim.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1 分类数据
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAll();
		request.setAttribute("categories", categories);
		
		// 分页数据 查询商品
		int page = 0;
		String pageParam = request.getParameter("page");
		if (pageParam == null) {
			page = 1;
		}else {
			page = Integer.parseInt(pageParam);
		}
		ProductService productService = new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findByPage(page);
		request.setAttribute("pageBean", pageBean);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
