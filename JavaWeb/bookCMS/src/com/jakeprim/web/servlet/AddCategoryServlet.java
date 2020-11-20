package com.jakeprim.web.servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jakeprim.service.CategoryService;
import com.jakeprim.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class AddCategoryServlet
 */
@WebServlet("/addCategory")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCategoryServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		//正则匹配ID是否合法
		String pattern = "^ca[0-9]{4}$";
		boolean isMatch = Pattern.matches(pattern, categoryId);
		if (!isMatch) {//没有匹配成功
			request.setAttribute("msg", "分类ID要求以ca开头，后面包括四位数字");
			request.getRequestDispatcher("addCategory.jsp").forward(request, response);
			return;
		}
		String categoryName = request.getParameter("categoryName");
		if (categoryName == null || categoryName == "") {
			request.setAttribute("msg", "分类名称不能为空");
			request.getRequestDispatcher("addCategory.jsp").forward(request, response);
			return;
		}
		//添加分类
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCatgory(categoryId, categoryName);
		//模拟保存数据到数据库中
		request.getServletContext().setAttribute("category", CategoryService.categoryDb);
		System.out.println(CategoryService.categoryDb);
		//回到分类列表页中
		response.sendRedirect(request.getContextPath()+"/categoryList.jsp");
	}

}
