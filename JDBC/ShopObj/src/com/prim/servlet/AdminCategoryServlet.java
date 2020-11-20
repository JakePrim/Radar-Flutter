package com.prim.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prim.domain.Category;
import com.prim.domain.Product;
import com.prim.service.CategoryService;
import com.prim.service.ProductService;
import com.prim.service.impl.CategoryServiceImpl;
import com.prim.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/admin/category")
public class AdminCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FIND_ALL = "findAll";
	
	private static final String SAVE_UI = "saveUI";
	
	private static final String SAVE = "save";
	
	private static final String EDIT = "edit"; 
	
	private static final String UPDATE = "update";
	
	private static final String DELETE = "delete";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if (FIND_ALL.equals(method)) {
			_findAllCategory(request,response);
		}else if (SAVE_UI.equals(method)) {
			_saveUI(request,response);
		}else if (SAVE.equals(method)) {
			_save(request,response);
		}else if (EDIT.equals(method)) {
			_findOnce(request,response);
		}else if (UPDATE.equals(method)) {
			_update(request,response);
		}else if (DELETE.equals(method)) {
			_delete(request,response);
		}
	}

	/**
	 * 删除某条分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void _delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int cid = Integer.valueOf(request.getParameter("cid"));
		CategoryService categoryService = new CategoryServiceImpl();
		boolean isDelete = categoryService.delete(cid);
		if (isDelete) {
			response.sendRedirect(request.getContextPath()+"/admin/category?method="+FIND_ALL);
		}else {
			System.out.println("删除失败,请查看日志");
		}
	}

	/**
	 * 更新某一条分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void _update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int cid = Integer.valueOf(request.getParameter("cid"));
		Category category = new Category();
		category.setCname(request.getParameter("cname"));
		category.setCdesc(request.getParameter("cdesc"));
		CategoryService categoryService = new CategoryServiceImpl();
		boolean isUpdate = categoryService.update(cid,category);
		if (isUpdate) {
			response.sendRedirect(request.getContextPath()+"/admin/category?method="+FIND_ALL);
		}else {
			System.out.println("更新数据失败,请查看日志");
		}
	}

	/**
	 * 查找某一条分类
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void _findOnce(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cid = Integer.valueOf(request.getParameter("cid"));
		CategoryService categoryService = new  CategoryServiceImpl();
		Category category = categoryService.findOnce(cid);
		if (category != null) {
			request.setAttribute("category", category);
			request.getRequestDispatcher("/admin/category_update.jsp").forward(request, response);
		}else {
			System.out.println("没有找到当前的数据,请检查数据库");
		}
	}

	private void _save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Category categroy = new Category();
		categroy.setCname(request.getParameter("cname"));
		categroy.setCdesc(request.getParameter("cdesc"));
		CategoryService categoryService = new CategoryServiceImpl();
		boolean isSave = categoryService.save(categroy);
		if (isSave) {
			response.sendRedirect(request.getContextPath()+"/admin/category?method="+FIND_ALL);
		}else {
			System.out.println("添加失败");
		}
	}

	/**
	 * 跳转到添加分类页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void _saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/admin/category_add.jsp").forward(request, response);
	}

	/**
	 * 查找所有的类型
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void _findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryService = new  CategoryServiceImpl();
		List<Category> categories = categoryService.findAll();// 查询到的分类列表 转发到jsp页面中
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/admin/category_list.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
