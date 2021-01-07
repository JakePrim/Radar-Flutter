package com.jakeprim.web.servlet;

import com.jakeprim.utils.UUIDUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet(name = "FileUploadServlet", urlPatterns = "/upload")
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            //1. 创建磁盘文件工厂对象
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
            //2. 创建文件上传核心类
            ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
            //2.1 设置上传文件的编码
            fileUpload.setHeaderEncoding("UTF-8");
            //2.2 判断表单是否是文件上传表单
            boolean multipartContent = fileUpload.isMultipartContent(request);
            //2.3 是文件上传表单
            if (multipartContent) {
                //3. 解析request 获取表单项的集合
                List<FileItem> parseRequest = fileUpload.parseRequest(request);
                if (parseRequest != null) {
                    //4. 遍历表单项的集合
                    for (FileItem fileItem : parseRequest) {
                        //5. 判断普通的表单项还是文件上传表单项
                        if (fileItem.isFormField()) {
                            //true普通的表单项
                            String fieldName = fileItem.getFieldName();
                            String fieldValue = fileItem.getString("UTF-8");//设置编码
                            System.out.println(fieldName + ":" + fieldValue);
                        } else {
                            //false 文件上传表单项
                            String fieldName = fileItem.getName();
                            //拼接新的文件名 使用UUID 保证不冲突
                            String newFieldName = UUIDUtils.getUUID() + "_" + fieldName;
                            //获取输入流
                            InputStream is = fileItem.getInputStream();
                            //创建输出流 输出的位置在tomcat/webapps/upload 文件夹下
                            //1. 获取项目的运行目录
                            String realPath = this.getServletContext().getRealPath("/");
                            String webappsPath = realPath.substring(0, realPath.indexOf("edu_api"));
                            FileOutputStream fos = new FileOutputStream(webappsPath + "/upload/" + newFieldName);
                            IOUtils.copy(is, fos);
                            fos.close();
                            is.close();
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
