package com.prim.controller;

import com.prim.biz.CategoryBiz;
import com.prim.biz.impl.CategoryBizImpl;
import com.prim.pojo.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author prim
 * /admin/Category/....
 */
public class CategoryController {

    private CategoryBiz categoryBiz;

    public CategoryController() {
        categoryBiz = new CategoryBizImpl();
    }

    /**
     * toList.do
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/category.jsp").forward(request, response);
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
        request.getRequestDispatcher("/WEB-INF/pages/admin/add_category.jsp").forward(request, response);
    }

    /**
     * add.do
     *
     * @param request
     * @param response
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        Category category = new Category();
        category.setName(name);
        category.setCreateTime(new Timestamp(System.currentTimeMillis()));
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        categoryBiz.addCategory(category);
        response.sendRedirect("list.do");
    }

    /**
     * list.do
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryBiz.selectAll();
        System.out.println("request = " + categories.toString());
        request.getServletContext().setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/pages/admin/category.jsp").forward(request, response);
    }
}
