package com.prim.servlet;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Map;

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
import com.prim.utils.FileUtils;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/admin/product")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String FIND_ALL = "findAll";

	private static final String SAVE_UI = "saveUI";

	private static final String SAVE = "save";

	private static final String DELETE = "delete";

	private static final String UPDATE = "update";

	private static final String UPDATE_UI = "updateUI";

	private ProductService productService;

	public ProductServlet() {
		super();
		productService = new ProductServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if (FIND_ALL.equals(method)) {
			_findAll(request, response);
		} else if (SAVE_UI.equals(method)) {
			_saveUI(request, response);
		} else if (SAVE.equals(method)) {
			_save(request, response);
		} else if (DELETE.equals(method)) {
			_delete(request, response);
		} else if (UPDATE.equals(method)) {
			_update(request, response);
		} else if (UPDATE_UI.equals(method)) {
			_updateUI(request, response);
		}
	}

	private void _updateUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查询当前数据
		int pid = Integer.valueOf(request.getParameter("pid"));
		Product product = productService.findOnce(pid);
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAll();
		request.setAttribute("product", product);
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/admin/product_update.jsp").forward(request, response);
	}

	private void _update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> infoMap = FileUtils.uploadImg(request, response);
		// 获取分类ID 查询分类
		int cid = Integer.valueOf(infoMap.get("cid"));
		int pid = Integer.valueOf(request.getParameter("pid"));
		Product product = new Product();
		product.setId(pid);
		product.getCategory().setId(cid);
		product.setPname(infoMap.get("pname"));
		product.setPrice(Double.valueOf(infoMap.get("price")));
		product.setDesc(infoMap.get("desc"));
		product.setAuthor(infoMap.get("author"));
		product.setPath(infoMap.get("path"));
		product.setFilename(infoMap.get("filename"));
		boolean isSave = productService.update(product,pid);
		if (isSave) {
			response.sendRedirect(request.getContextPath() + "/admin/product?method=" + FIND_ALL);
		} else {
			System.out.println("添加数据失败,请查看日志");
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void _delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int pid = Integer.valueOf(request.getParameter("pid"));
		Product product = productService.findOnce(pid);
		String path  = product.getPath();
		if (path != null && !"".equals(path)) {
			path.replace("/ShopObj", "");
			System.out.println("path:"+path);
			String relPath = this.getServletContext().getRealPath(path);
			System.out.println("relPath:"+relPath);
			File file = new File(relPath);
			System.out.println("exists:"+file.exists());
			if (file.exists()) {
				file.delete();
			}
		}
		//注意图片还没有删除以下只是删除了数据库记录
		boolean isDelete = productService.delete(pid);
		if (isDelete) {
			response.sendRedirect(request.getContextPath() + "/admin/product?method=" + FIND_ALL);
		} else {
			System.out.println("删除失败,请查看日志");
		}
	}

	/**
	 * 保存到数据库中
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void _save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Map<String, String> infoMap = FileUtils.uploadImg(request, response);
		// 获取分类ID 查询分类
		int cid = Integer.valueOf(infoMap.get("cid"));
		Product product = new Product();
		product.getCategory().setId(cid);
		product.setPname(infoMap.get("pname"));
		product.setPrice(Double.valueOf(infoMap.get("price")));
		product.setDesc(infoMap.get("desc"));
		product.setAuthor(infoMap.get("author"));
		product.setPath(infoMap.get("path"));
		product.setFilename(infoMap.get("filename"));
		boolean isSave = productService.save(product);
		if (isSave) {
			response.sendRedirect(request.getContextPath() + "/admin/product?method=" + FIND_ALL);
		} else {
			System.out.println("添加数据失败,请查看日志");
		}

	}

	/**
	 * 调出添加页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void _saveUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categories = categoryService.findAll();
		request.setAttribute("categories", categories);
		request.getRequestDispatcher("/admin/product_add.jsp").forward(request, response);
	}

	/**
	 * 查询所有商品
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void _findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService productService = new ProductServiceImpl();
		List<Product> products = productService.findAll();
		request.setAttribute("products", products);
		request.getRequestDispatcher("/admin/product_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
