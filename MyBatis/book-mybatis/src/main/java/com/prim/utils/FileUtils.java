package com.prim.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author prim
 */
public class FileUtils {
    public static String getUUIDName(String name) {
        int index = name.lastIndexOf(".");
        //.jpg
        String exName = name.substring(index);
        return UUID.randomUUID().toString() + exName;
    }

    public static Map<String, String> uploadImg(HttpServletRequest request, HttpServletResponse response) {
        // enctype 属性修改了 这一块都需要进行修改
        Map<String, String> infoMap = new HashMap<String, String>(10);
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
                        // 获取输入值 UTF-8 解决中文乱码问题
                        String value = fileItem.getString("UTF-8");
                        infoMap.put(name, value);
                    } else {
                        // false 表示为文件上传项
                        // 获取文件名 注意:文件名重名的问题
                        String fileName = fileItem.getName();
                        System.out.println("fileName:" + fileName + " value:" + fileItem.getString("UTF-8"));
                        if (fileName != null && !"".equals(fileName)) {
                            //每个注册的用户都要生成唯一的文件名
                            String uuidName = FileUtils.getUUIDName(fileName);
                            // 获取文件的输入流 准备写入服务器
                            InputStream stream = fileItem.getInputStream();
                            // 获取存入服务器的路径
                            String path = request.getServletContext().getRealPath("/");
                            // 创建输出流 写入服务器
                            OutputStream os = new FileOutputStream(path + "/download/images/" + uuidName);
                            infoMap.put("path", "/download/images/" + uuidName);
                            infoMap.put("filename", uuidName);
                            int leg = 0;
                            byte[] b = new byte[1024];
                            while ((leg = stream.read(b)) != -1) {
                                os.write(b, 0, leg);
                            }
                            stream.close();
                            os.close();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return infoMap;
    }


}
