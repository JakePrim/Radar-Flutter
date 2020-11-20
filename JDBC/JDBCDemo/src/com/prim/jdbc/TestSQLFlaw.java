package com.prim.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestSQLFlaw {
	public static void main(String[] args) {
		//测试SQL注入漏洞
//		boolean isLogin = login("aaa' or '1=1", "1231");
//		//类似 SELECT * FROM user2 WHERE username= 'aaa' or '1=1' AND password=''
//		//或者通过-- SQL中的注释 屏蔽掉密码  SELECT * FROM user2 WHERE username= 'aaa' -- ' AND password=''
//		if (isLogin) {
//			System.out.println("登录成功");
//		}else {
//			System.out.println("登录失败");
//		}
//		update("acac", 1);
//		delete(2);
		show();
	}
	
	//PreparedStatement 的基本的操作
	//插入操作
	public static void insert(String username,String password) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = JDBCUtils.getConnection();
			pstmt = connection.prepareStatement("INSERT user2(username,password) VALUES(?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("插入操作完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, pstmt);
		}
	}
	
	public static void update(String username,int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = JDBCUtils.getConnection();
			pstmt = connection.prepareStatement("UPDATE user2 SET username = ? WHERE id = ?");
			pstmt.setString(1, username);
			pstmt.setInt(2, id);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("更新操作完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, pstmt);
		}
	}
	
	public static void delete(int id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		try {
			connection = JDBCUtils.getConnection();
			pstmt = connection.prepareStatement("DELETE FROM user2 WHERE id=?");
			pstmt.setInt(1, id);
			int count = pstmt.executeUpdate();
			if (count > 0) {
				System.out.println("删除操作完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, pstmt);
		}
	}
	
	public static void show() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			pstmt = connection.prepareStatement("SELECT * FROM user2");
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id")+"   "+resultSet.getString("username")+"   "+resultSet.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, pstmt,resultSet);
		}
	}
	
	public static boolean login(String username,String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		boolean flag = false;
		try {
			connection = JDBCUtils.getConnection();
			String sql = "SELECT * FROM user2 WHERE username=? AND password=?"; // 通过预编译 将SQL固定样式 不允许修改
			statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			resultSet = statement.executeQuery();
			flag = resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return flag;
	}
	
	public static boolean loginFlaw(String username,String password) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		boolean flag = false;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM user2 WHERE username= '"+username+"' AND password='"+password+"'");
			flag = resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return flag;
	}

}
