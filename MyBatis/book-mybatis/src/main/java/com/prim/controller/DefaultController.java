package com.prim.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.prim.biz.BookBiz;
import com.prim.biz.impl.BookBizImpl;
import com.prim.pojo.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author prim
 */
public class DefaultController {
    /**
     * index.do
     *
     * @param request
     * @param response
     */
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取图书列表信息
        int page = 1;
        String pageNum = "pageNum";
        if (request.getParameter(pageNum) != null) {
            page = Integer.parseInt(request.getParameter(pageNum));
        }
        PageHelper.startPage(page, 20);
        BookBiz bookBiz = new BookBizImpl();
        List<Book> bookList = null;
        String id = request.getParameter("id");
        if (id != null && !"".equals(id)) {
            int cid = Integer.parseInt(id);
            request.setAttribute("cid", cid);
            bookList = bookBiz.selectBookByCid(cid);
        } else {
            bookList = bookBiz.selectAll();
        }
        PageInfo<Book> pageInfo = PageInfo.of(bookList);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/WEB-INF/pages/front/index.jsp").forward(request, response);
    }
}
