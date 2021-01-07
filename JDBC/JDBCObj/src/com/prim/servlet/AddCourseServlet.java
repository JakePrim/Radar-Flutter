package com.prim.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
 * Servlet implementation class AddCourseServlet
 */
@WebServlet("/add")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddCourseServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String desp = request.getParameter("desp");
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils2.getConnection();
			statement = connection.prepareStatement("INSERT course(name,category,desp,createtime) VALUES(?,?,?,?)");
			statement.setString(1, name);
			statement.setString(2, category);
			statement.setString(3, desp);
			statement.setDate(4, new Date(System.currentTimeMillis()));
			int num = statement.executeUpdate();
			if (num > 0) {
				System.out.println("插入数据成功");
				response.sendRedirect(request.getContextPath()+"/show");
			}else {
				System.out.println("插入数据失败");
				response.sendRedirect(request.getContextPath()+"/fail.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/fail.jsp");
		}finally {
			JDBCUtils2.close(connection, statement);
		}
	}

}
