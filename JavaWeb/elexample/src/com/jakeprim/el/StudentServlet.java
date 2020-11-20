package com.jakeprim.el;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/info")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Student  student = new Student();
		student.setName("JakePrim1");
		student.setMoblie(null);
		String grade = "A";
		HttpSession session = request.getSession();
		session.setAttribute("student", student);
		
		request.setAttribute("grade", "D");
		
		session.setAttribute("grade", "C");
		
		request.getServletContext().setAttribute("grade", "B");
		
		request.getRequestDispatcher("/el_info.jsp").forward(request, response);
	}

}
