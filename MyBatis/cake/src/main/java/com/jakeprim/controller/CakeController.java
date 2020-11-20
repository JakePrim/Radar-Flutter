package com.jakeprim.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jakeprim.biz.CakeBiz;
import com.jakeprim.biz.CategoryBiz;
import com.jakeprim.biz.impl.CakeBizImpl;
import com.jakeprim.biz.impl.CategoryBizImpl;
import com.jakeprim.dto.CakeDTO;
import com.jakeprim.pojo.Cake;
import com.jakeprim.pojo.Category;
import com.jakeprim.utils.FileUtils;
import com.sun.tools.javac.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 */
public class CakeController {

    private CakeBiz cakeBiz;

    public CakeController() {
        cakeBiz = new CakeBizImpl();
    }

    /**
     * 获取所有商品
     * /admin/Cake/list.do
     *
     * @param request
     * @param response
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNum = 1;
        String num = request.getParameter("pageNum");
        if (null != num && !"".equals(num)) {
            pageNum = Integer.parseInt(num);
        }
        PageHelper.startPage(pageNum, 20);
        List<CakeDTO> cakeDTOList = cakeBiz.selectAll();
        PageInfo pageInfo = PageInfo.of(cakeDTOList);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_list.jsp").forward(request, response);
    }

    /**
     * 删除某个商品
     * /admin/Cake/delete.do
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int delete = cakeBiz.delete(id);
        if (delete > 0) {
            response.sendRedirect("list.do");
        } else {
            System.out.println("delete 错误请查看日志");
        }
    }

    /**
     * 跳转到添加商品页面
     * /admin/Cake/toAdd.do
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //查询分类
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_add.jsp").forward(request, response);
    }

    /**
     * 将商品信息添加到数据库
     * /admin/Cake/add.do
     *
     * @param request
     * @param response
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取传递的参数一堆 TODO 有什么好的办法？
        Map<String, String> map = FileUtils.uploadImg(request, response);
        String title = map.get("title");
        int cid = Integer.parseInt(map.get("cid"));
        String image_path = map.get("path");
        double price = Double.parseDouble(map.get("price"));
        String taste = map.get("taste");
        int sweetness = Integer.parseInt(map.get("sweetness"));
        double weight = Double.parseDouble(map.get("weight"));
        int size = Integer.parseInt(map.get("size"));
        String material = map.get("material");
        String status = map.get("status");
        Cake cake = new Cake();
        cake.setCid(cid);
        cake.setTitle(title);
        cake.setImagePath(image_path);
        cake.setMaterial(material);
        cake.setPrice(price);
        cake.setStatus(status);
        cake.setSweetness(sweetness);
        cake.setTaste(taste);
        cake.setWeight(weight);
        cake.setSize(size);
        int insert = cakeBiz.insert(cake);
        if (insert > 0) {
            response.sendRedirect("list.do");
        }
    }

    /**
     * 查看商品信息
     * /admin/Cake/toView.do
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CakeDTO cakeDTO = cakeBiz.selectById(id);
        request.setAttribute("cakeDto", cakeDTO);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_info.jsp").forward(request, response);
    }

    /**
     * 跳转到商品编辑页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CakeDTO cakeDTO = cakeBiz.selectById(id);
        CategoryBiz categoryBiz = new CategoryBizImpl();
        request.setAttribute("cakeDto", cakeDTO);
        request.getRequestDispatcher("/WEB-INF/pages/admin/cake_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Map<String, String> map = FileUtils.uploadImg(request, response);
        Cake cake = new Cake();
        cake.setId(id);
        cake.setCid(Integer.parseInt(map.get("cid")));
        cake.setTitle(map.get("title"));
        cake.setImagePath(map.get("path") == null ? map.get("imagePath") : map.get("path"));
        cake.setMaterial(map.get("material"));
        cake.setPrice(Double.parseDouble(map.get("price")));
        cake.setStatus(map.get("status"));
        cake.setSweetness(Integer.parseInt(map.get("sweetness")));
        cake.setTaste(map.get("taste"));
        cake.setWeight(Double.parseDouble(map.get("weight")));
        cake.setSize(Integer.parseInt(map.get("size")));
        int update = cakeBiz.update(cake);
        response.sendRedirect("list.do");
    }
}
