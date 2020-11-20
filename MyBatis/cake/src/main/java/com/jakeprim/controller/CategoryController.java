package com.jakeprim.controller;

import com.jakeprim.biz.CategoryBiz;
import com.jakeprim.biz.impl.CategoryBizImpl;
import com.jakeprim.pojo.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类数据控制器
 *
 * @author prim
 */
public class CategoryController {

    private static final String TAG = "CategoryController";

    private CategoryBiz categoryBiz;

    public CategoryController() {
        categoryBiz = new CategoryBizImpl();
    }

    /**
     * 获取所有分类列表
     * /admin/Category/list.do
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(TAG + " list() ");
        Category categoryDTO = categoryBiz.getRoot();
        request.getServletContext().setAttribute("root", categoryDTO);
        request.getRequestDispatcher("/WEB-INF/pages/admin/category_list.jsp").forward(request, response);
    }

    /**
     * :/admin/Category/toAdd.do
     * 跳转到分类添加页面
     *
     * @param request
     * @param response
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //添加分类页面需要用到所有的分类数据
        // 跳转到相应的页面
        request.getRequestDispatcher("/WEB-INF/pages/admin/category_add.jsp").forward(request, response);
    }

    /**
     * 添加分类
     * /admin/Category/add.do
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //可能获取到的是多个数据 TODO 注意分类名字重复的问题
        String[] titles = request.getParameterValues("title");
        String[] pids = request.getParameterValues("pid");
        String[] infos = request.getParameterValues("info");
        List<Category> categories = new ArrayList<Category>();
        for (int i = 0; i < titles.length; i++) {
            //如果存在相同到分类标题这不允许添加
            if (categoryBiz.selectByTitleCount(titles[i])) {
                continue;
            }
            Category category = new Category();
            category.setTitle(titles[i]);
            category.setPid(Integer.valueOf(pids[i]));
            category.setInfo(infos[i]);
            categories.add(category);
        }
        int add = categoryBiz.add(categories);
        if (add > 0) {
            response.sendRedirect("list.do");
        } else {
            response.sendRedirect("list.do");
            System.out.println("add.do = 删除异常请查看日志");
        }
    }

    /**
     * 删除分类
     * /admin/Category/delete.do?id=....
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO 如果删除的分类是某些子类的父类 需要特殊处理
        int id = Integer.parseInt(request.getParameter("id"));

        int delete = categoryBiz.delete(id);
        if (delete > 0) {
            response.sendRedirect("list.do");
        } else {
            System.out.println("delete.do = 删除异常请查看日志");
        }
    }
}
