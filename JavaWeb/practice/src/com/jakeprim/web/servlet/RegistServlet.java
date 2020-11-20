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

import com.jakeprim.domain.User;
import com.jakeprim.service.UserService;
import com.jakeprim.service.impl.UserServiceImpl;
import com.jakeprim.utils.FileUtils;

/**
 * Servlet implementation class RegistServlet
 */
@WebServlet("/regist")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// enctype 属性修改了 这一块都需要进行修改
		Map<String, String> infoMap = new HashMap<String, String>();
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
						//每个注册的用户都要生成唯一的文件名
						String uuidName = FileUtils.getUUIDName(fileName);
						// 获取文件的输入流 准备写入服务器
						InputStream stream = fileItem.getInputStream();
						// 获取存入服务器的路径
						String path = request.getServletContext().getRealPath("/upload");
						// 创建输出流 写入服务器
						OutputStream os = new FileOutputStream(path + "//" + uuidName);
						infoMap.put("path", request.getContextPath()+"/upload/"+uuidName);
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
			User user = new User();
			user.setUsername(infoMap.get("username"));
			user.setPassword(infoMap.get("password"));
			user.setPath(infoMap.get("path"));
			success(request, response, user);
		} else {//如果没有修改enctype 则不对文件进行处理
			// 获取注册信息
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			// 保存用户信息
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			success(request, response, user);
		}
	}
	
	private void success(HttpServletRequest request, HttpServletResponse response,User user) throws IOException {
		List<User> users = (List<User>) request.getServletContext().getAttribute("users");
		UserService userService = new UserServiceImpl();
		userService.regist(users, user);
		System.out.println(user.toString());
		// 注册成功后跳转
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

}
