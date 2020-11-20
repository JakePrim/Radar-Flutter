package com.prim.controller;

import com.prim.biz.BookBiz;
import com.prim.biz.impl.BookBizImpl;
import com.prim.pojo.Book;
import com.prim.utils.FileUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * /admin/Book/..
 *
 * @author prim
 */
public class BookController {

    private BookBiz bookBiz;

    public BookController() {
        bookBiz = new BookBizImpl();
    }

    public void books() {

    }


    public BookController(BookBiz bookBiz) {
        this.bookBiz = bookBiz;
    }


    /**
     * toAdd.do
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        System.out.println("request = " + cid);
        request.getRequestDispatcher("/WEB-INF/pages/admin/add_book.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) {
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
                int count = 0;
                List<Book> bookList = new ArrayList<>();
                Book book = new Book();
                for (FileItem fileItem : fileItems) {
                    count++;
                    if (fileItem.isFormField()) {
                        // true 表示为普通项 存储用户名 密码
                        // 获取名称
                        String name = fileItem.getFieldName();
                        // 获取输入值 UTF-8 解决中文乱码问题
                        String value = fileItem.getString("UTF-8");
                        if ("categoryId".equals(name)) {
                            book.setCategoryId(Integer.parseInt(value));
                        }
                        if ("name".equals(name)) {
                            book.setName(value);
                        }
                        if ("level".equals(name)) {
                            book.setLevel(Integer.parseInt(value));
                        }
                        if ("price".equals(name)) {
                            book.setPrice(Integer.parseInt(value));
                        }
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
                            book.setImgPath("/download/images/" + uuidName);
                            int leg = 0;
                            byte[] b = new byte[1024];
                            while ((leg = stream.read(b)) != -1) {
                                os.write(b, 0, leg);
                            }
                            stream.close();
                            os.close();
                        }
                    }
                    if (count % 5 == 0) {
                        book.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        book.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        bookList.add(book);
                        count = 0;
                        book = new Book();
                    }
                }
                bookBiz.batchBook(bookList);
                response.sendRedirect("toBooks.do?id=" + bookList.get(0).getCategoryId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * toBooks.do
     */
    public void toBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Book> books = bookBiz.selectBookByCid(id);
        System.out.println("request = " + books);
        request.setAttribute("books", books);
        request.setAttribute("cid", id);
        request.getRequestDispatcher("/WEB-INF/pages/admin/book.jsp").forward(request, response);
    }
}
