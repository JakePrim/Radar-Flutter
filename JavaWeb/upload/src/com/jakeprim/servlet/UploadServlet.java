package com.jakeprim.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//判断form表单是否设置了enctype="multipart/form-data" 如果没有设置则不在执行
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			//建立磁盘文件项工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//创建核心解析类
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			//解析请求对象 拆分文件上传项部分和普通项部分
			try {
				List<FileItem> fileItems = servletFileUpload.parseRequest(request);
				for (FileItem fileItem : fileItems) {
					if (fileItem.isFormField()) {
						//true 表示为普通项
						//获取名称
						String name = fileItem.getFieldName();
						String value = fileItem.getString("UTF-8");//获取输入值 UTF-8 解决中文乱码问题
						System.out.println(name+":"+value);
					}else {
						//false 表示为文件上传项
						//获取文件名
						String fileName = fileItem.getName();
						System.out.println("fileName:"+fileName);
						//获取文件的输入流 准备写入服务器
						InputStream stream = fileItem.getInputStream();
						//获取存入服务器的路径
						String path = request.getServletContext().getRealPath("/upload");
						System.out.println("存入路径:"+path);
						//创建输出流 写入服务器
						OutputStream os = new FileOutputStream(path+"//"+fileName);
						int leg = 0;
						byte[] b = new byte[1024];
						while((leg = stream.read(b)) != -1) {
							os.write(b, 0, leg);
						}
						stream.close();
						os.close();
						response.getWriter().println("文件上传成功");
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			response.getWriter().println("form表单需要设置enctype=\"multipart/form-data\"");
		}
	}

}
