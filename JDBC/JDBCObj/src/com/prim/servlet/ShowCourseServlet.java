package com.prim.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prim.utils.JDBCUtils2;

/**
 * Servlet implementation class ShowCourseServlet
 */
@WebServlet("/show")
public class ShowCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowCourseServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			List<Course> courses = new ArrayList<Course>();
			connection = JDBCUtils2.getConnection();
			statement = connection.prepareStatement("SELECT name,category,desp,createtime FROM course");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Course course = new Course();
				course.setName(resultSet.getString("name"));
				course.setCategory(resultSet.getString("category"));
				course.setDesp(resultSet.getString("desp"));
				course.setCreatetime(resultSet.getDate("createtime"));
				courses.add(course);
			}
			request.setAttribute("courses", courses);
			request.getRequestDispatcher("showCourse.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/fail.jsp");
		}finally {
			JDBCUtils2.close(connection, statement, resultSet);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
