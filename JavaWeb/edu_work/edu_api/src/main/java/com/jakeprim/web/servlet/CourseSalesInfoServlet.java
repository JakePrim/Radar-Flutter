package com.jakeprim.web.servlet;

import com.jakeprim.base.Constants;
import com.jakeprim.pojo.Course;
import com.jakeprim.service.CourseService;
import com.jakeprim.service.impl.CourseServiceImpl;
import com.jakeprim.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 保存和修改课程营销信息
 */
@WebServlet(name = "CourseSalesInfoServlet", urlPatterns = "/courseSalesInfo")
public class CourseSalesInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1. 创建Course对象
            Course course = new Course();
            //2. 创建map集合收集数据
            Map<String, Object> paramsMap = new HashMap<>();
            //3. 文件上传
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);//文件上传核心对象
            //解析request对象 获取表单项的集合
            List<FileItem> list = upload.parseRequest(request);
            boolean multipartContent = ServletFileUpload.isMultipartContent(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    //普通表单项
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("utf-8");
                    System.out.println(fieldName + ":" + fieldValue);
                    //保存到参数集合中
                    paramsMap.put(fieldName, fieldValue);
                } else {
                    //文件上传项
                    String name = item.getName();//获取文件名
                    String newName = UUIDUtils.getUUID() + "_" + name;//设置新的文件名
                    //获取输入流
                    InputStream is = item.getInputStream();
                    //创建输出流 输出的位置在tomcat/webapps/upload 文件夹下
                    //1. 获取项目的运行目录
                    String realPath = this.getServletContext().getRealPath("/");
                    String webappsPath = realPath.substring(0, realPath.indexOf("edu_api"));
                    FileOutputStream fos = new FileOutputStream(webappsPath + "/upload/" + newName);
                    IOUtils.copy(is, fos);
                    //关闭流
                    fos.close();
                    is.close();
                    //保存参数
                    paramsMap.put("course_img_url", Constants.LOCAL_URL + "/upload/" + newName);
                }
            }
            //4. 使用BeanUtils 将map中的数据封装到JavaBean中
            BeanUtils.populate(course, paramsMap);
            if (paramsMap.get("id") != null) {
                //修改操作
                //5. 数据业务操作
                CourseService courseService = new CourseServiceImpl();
                String result = courseService.updateCourseSalesInfo(course);
                response.getWriter().print(result);
            } else {
                //新建操作
                //5. 数据业务操作
                CourseService courseService = new CourseServiceImpl();
                String result = courseService.saveCourseSalesInfo(course);
                response.getWriter().print(result);
            }
        } catch (FileUploadException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
