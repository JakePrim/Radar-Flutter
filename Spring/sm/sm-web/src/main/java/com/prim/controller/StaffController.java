package com.prim.controller;

import com.prim.pojo.Department;
import com.prim.pojo.Staff;
import com.prim.service.DepartmentService;
import com.prim.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author prim
 */
@Controller("staffController")
public class StaffController {

    @Autowired
    @Qualifier("staffService")
    private StaffService staffService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * department/list.do
     *
     * @param request
     * @param response
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Staff> staffList = staffService.getAll();
        request.setAttribute("staffList", staffList);
        request.getRequestDispatcher(request.getContextPath() + "/staff_list.jsp").forward(request, response);
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
        staffService.remove(id);
        response.sendRedirect("list.do");
    }

    /**
     * /staff/toAdd.do
     *
     * @param request
     * @param response
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getAll();
        request.setAttribute("departments", departments);
        request.getRequestDispatcher(request.getContextPath() + "/staff_add.jsp").forward(request, response);
    }

    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff staff = staffService.get(id);
        request.setAttribute("staff", staff);
        request.getRequestDispatcher(request.getContextPath() + "/staff_detail.jsp").forward(request, response);
    }

    /**
     * /department/add.do
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        Staff staff = new Staff();
        staff.setAccount(request.getParameter("account"));
        staff.setDid(Integer.parseInt(request.getParameter("did")));
        staff.setName(request.getParameter("name"));
        staff.setSex(request.getParameter("sex"));
        staff.setIdNumber(request.getParameter("idNumber"));
        String bornDate = request.getParameter("bornDate");
        staff.setBornDate(new SimpleDateFormat("yyyy-MM-dd").parse(bornDate));
        staff.setInfo(request.getParameter("info"));
        Department department = departmentService.get(Integer.parseInt(request.getParameter("did")));
        staff.setDepartment(department);
        staffService.add(staff);
        response.sendRedirect("list.do");
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Staff staff = staffService.get(id);
        List<Department> departments = departmentService.getAll();
        request.setAttribute("departments", departments);
        request.setAttribute("staff", staff);
        request.getRequestDispatcher(request.getContextPath() + "/staff_edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        //通过id获取数据，在获取的数据上进心修改
        Staff staff = staffService.get(Integer.parseInt(request.getParameter("id")));
        //设置修改数据
        staff.setAccount(request.getParameter("account"));
        staff.setDid(Integer.parseInt(request.getParameter("did")));
        staff.setName(request.getParameter("name"));
        staff.setSex(request.getParameter("sex"));
        staff.setIdNumber(request.getParameter("idNumber"));
        String bornDate = request.getParameter("bornDate");
        staff.setBornDate(new SimpleDateFormat("yyyy-MM-dd").parse(bornDate));
        staff.setInfo(request.getParameter("info"));
        staffService.edit(staff);
        response.sendRedirect("list.do");
    }
}
