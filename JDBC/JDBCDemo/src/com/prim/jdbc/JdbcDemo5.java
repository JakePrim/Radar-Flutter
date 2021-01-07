package com.prim.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JdbcDemo5 {
	public static void main(String[] args) {
		insert("Java零基础", "Java", "Java基础语法");
		insert("JavaWeb", "Java", "JSP Servlet");
		insert("前端小白", "前端", "HTML.CSS.JS");
//		show();
//		update(1, "Java语法");
		delete(2);
	}
	
	public static void insert(String name,String category,String desp) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("INSERT course(name,category,desp,createtime) VALUES(?,?,?,?)");
			statement.setString(1, name);
			statement.setString(2, category);
			statement.setString(3, desp);
			statement.setDate(4, new Date(System.currentTimeMillis()));
			int num = statement.executeUpdate();
			if (num > 0) {
				System.out.println("插入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
	}
	
	public static void update(int id,String name) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("UPDATE course SET name = ? WHERE id = ?");
			statement.setString(1, name);
			statement.setInt(2, id);
			int num = statement.executeUpdate();
			if (num > 0) {
				System.out.println("更新成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
	}
	public static void delete(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("DELETE FROM course WHERE id = ?");
			statement.setInt(1, id);
			int num = statement.executeUpdate();
			if (num > 0) {
				System.out.println("删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
	}
	
	
	public static void show() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT * FROM course");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id") +"   "+resultSet.getString("name")+"   "+
			resultSet.getString("category")+"   "+resultSet.getString("desp"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
	}
}
