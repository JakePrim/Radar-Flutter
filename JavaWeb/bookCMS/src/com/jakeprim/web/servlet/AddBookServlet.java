package com.jakeprim.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jakeprim.domain.Book;
import com.jakeprim.domain.Category;
import com.jakeprim.domain.User;
import com.jakeprim.service.BookService;
import com.jakeprim.service.UserService;
import com.jakeprim.service.impl.BookServiceImpl;
import com.jakeprim.service.impl.UserServiceImpl;
import com.jakeprim.utils.FileUtils;

/**
 * Servlet implementation class AddBookServlet
 */
@WebServlet("/dept/add.do")
public class AddBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Map<String, String> infoMap = new HashMap<String, String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 判断form表单是否设置了enctype="multipart/form-data" 如果没有设置则不在执行
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			// 建立磁盘文件项工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			// 创建核心解析类
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			// 解析请求对象 拆分文件上传项部分和普通项部分
			try {
				List<FileItem> fileItems = servletFileUpload.parseRequest(request);
				for (FileItem fileItem : fileItems) {
					if (fileItem.isFormField()) {
						// true 表示为普通项 存储用户名 密码
						// 获取名称
						String name = fileItem.getFieldName();
						String value = fileItem.getString("UTF-8");// 获取输入值 UTF-8 解决中文乱码问题
						infoMap.put(name, value);
					} else {
						// false 表示为文件上传项
						// 获取文件名 注意:文件名重名的问题
						String fileName = fileItem.getName();
						// 每个注册的用户都要生成唯一的文件名
						String uuidName = FileUtils.getUUIDName(fileName);
						// 获取文件的输入流 准备写入服务器
						InputStream stream = fileItem.getInputStream();
						// 获取存入服务器的路径
						String path = request.getServletContext().getRealPath("/upload");
						// 创建输出流 写入服务器
						OutputStream os = new FileOutputStream(path + "//" + uuidName);
						infoMap.put("path", request.getContextPath() + "/upload/" + uuidName);
						int leg = 0;
						byte[] b = new byte[1024];
						while ((leg = stream.read(b)) != -1) {
							os.write(b, 0, leg);
						}
						stream.close();
						os.close();
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Book book = new Book();
			book.setBookId(infoMap.get("bookId"));
			book.setBookName(infoMap.get("bookName"));
			List<Category> categories = (List<Category>) request.getServletContext().getAttribute("category");
			for (Category category : categories) {
				if (category.getCategoryId().equals(infoMap.get("categoryId"))) {
					book.setCategory(category);
					break;
				}
			}
			book.setNote(infoMap.get("remarks"));
			book.setPath(infoMap.get("path"));
			String bookPrice = infoMap.get("bookPrice");
			book.setPrice(Double.parseDouble(bookPrice));
			
			success(request, response, book);
		} else {// 如果没有修改enctype 则不对文件进行处理
			// 获取注册信息
			String bookId = request.getParameter("bookId");
			String bookName = request.getParameter("bookName");
			String categoryId = request.getParameter("categoryId");
			String remarks = request.getParameter("remarks");
			String bookPrice = request.getParameter("bookPrice");
			
			Book book = new Book();
			book.setBookId(bookId);
			book.setBookName(bookName);
			List<Category> categories = (List<Category>) request.getServletContext().getAttribute("category");
			for (Category category : categories) {
				if (category.getCategoryId().equals(categoryId)) {
					book.setCategory(category);
					break;
				}
			}
			book.setNote(remarks);
			book.setPrice(Double.parseDouble(bookPrice));
			
			success(request, response, book);
		}
	}
	
	private void success(HttpServletRequest request, HttpServletResponse response,Book book) throws IOException {
		BookService bookService = new BookServiceImpl();
		bookService.addBook(book);
		request.getServletContext().setAttribute("books", BookService.bookDb);
		System.out.println(BookService.bookDb);
		// 注册成功后跳转
		response.sendRedirect(request.getContextPath() + "/bookList.jsp");
	}

}
