package com.jakeprim.employee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// 处理中文乱码问题
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String department = request.getParameter("department");
		String job = request.getParameter("job");
		String salary = request.getParameter("salary");
		ServletContext context = request.getServletContext();
		List<Employee> employees = (List<Employee>) context.getAttribute("employees");
		if (employees != null) {
			try {
				employees
						.add(new Employee(Integer.parseInt(empno), ename, department, job, Double.parseDouble(salary)));
			} catch (Exception e) {
				response.getWriter().println("参数错误");
			}
		} else {
			employees = new ArrayList<Employee>();
			employees.add(new Employee(Integer.parseInt(empno), ename, department, job, Double.parseDouble(salary)));
		}
//		context.setAttribute("employees", employees);
		//刷新页面
		request.getRequestDispatcher("/employee.jsp").forward(request, response);
	}

}
