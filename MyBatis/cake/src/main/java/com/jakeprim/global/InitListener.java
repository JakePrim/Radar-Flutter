package com.jakeprim.global;

import com.jakeprim.biz.CategoryBiz;
import com.jakeprim.biz.impl.CategoryBizImpl;
import com.jakeprim.pojo.Category;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitListener implements ServletContextListener {

    CategoryBiz categoryBiz = new CategoryBizImpl();

    public void contextInitialized(ServletContextEvent sce) {
        Category root = categoryBiz.getRoot();
        sce.getServletContext().setAttribute("root", root);
    }
}
