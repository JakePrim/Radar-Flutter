package com.jakeprim.controller;

import com.jakeprim.biz.CakeBiz;
import com.jakeprim.biz.CategoryBiz;
import com.jakeprim.biz.impl.CakeBizImpl;
import com.jakeprim.biz.impl.CategoryBizImpl;
import com.jakeprim.dao.AccountDao;
import com.jakeprim.dto.CakeDTO;
import com.jakeprim.global.DaoFactory;
import com.jakeprim.pojo.Account;
import com.jakeprim.pojo.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DefaultController {
    //login.do
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        AccountDao dao = DaoFactory.getInstance().getDao(AccountDao.class, true);
        Account account1 = dao.selectByAccount(account, password);
        if (account1 == null) {
            response.sendRedirect("toLogin.do");
        } else {
            request.getSession().setAttribute("account", account1);
            response.sendRedirect("/admin/Cake/list.do");
        }
    }

    //toLogin.do
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/login.jsp").forward(request, response);
    }

    //quit.do
    public void quit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("account", null);
        response.sendRedirect("toLogin.do");
    }

    private CakeBiz cakeBiz = new CakeBizImpl();
    private CategoryBiz categoryBiz = new CategoryBizImpl();

    //index.do
    //分类 特卖 推荐
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CakeDTO> cakeDTOS = cakeBiz.selectByStatus("特卖");
        if (cakeDTOS.size() > 0) {
            request.setAttribute("cake", cakeDTOS.get(0));
        }
        List<CakeDTO> list = cakeBiz.selectByStatus("推荐");
        request.setAttribute("list", list);
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
    }
}
