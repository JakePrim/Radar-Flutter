package com.prim.controller;

import com.prim.pojo.Department;
import com.prim.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 控制器@Controller注入交由Spring实例化类
 *
 * @author prim
 */
@Controller("departmentController")
public class DepartmentController {

    /**
     * 找到departmentService id 实例化DepartmentServiceImpl类
     */
    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;

    /**
     * department/list.do
     *
     * @param request
     * @param response
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getAll();
        request.setAttribute("departments", departments);
        request.getRequestDispatcher(request.getContextPath() + "/department_list.jsp").forward(request, response);
    }

    /**
     * /department/remove.do
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        departmentService.remove(id);
        response.sendRedirect("list.do");
    }

    /**
     * /department/toAdd.do
     *
     * @param request
     * @param response
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/department_add.jsp").forward(request, response);
    }

    /**
     * /department/add.do
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        Department department = new Department();
        department.setName(name);
        department.setAddress(address);
        departmentService.add(department);
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Department department = departmentService.get(id);
        request.setAttribute("department", department);
        request.getRequestDispatcher(request.getContextPath() + "/department_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Department department = new Department();
        department.setId(Integer.valueOf(request.getParameter("id")));
        department.setName(request.getParameter("name"));
        department.setAddress(request.getParameter("address"));
        departmentService.edit(department);
        response.sendRedirect("list.do");
    }
}
