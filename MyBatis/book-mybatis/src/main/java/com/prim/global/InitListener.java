package com.prim.global;

import com.prim.biz.CategoryBiz;
import com.prim.biz.impl.CategoryBizImpl;
import com.prim.pojo.Category;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class InitListener implements ServletContextListener {

    CategoryBiz categoryBiz = new CategoryBizImpl();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<Category> categories = categoryBiz.selectAll();
        sce.getServletContext().setAttribute("categories", categories);
    }
}
