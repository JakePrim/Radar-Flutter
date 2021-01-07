package com.prim.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo3 {
	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			//注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql:///king", "root", "root");
			statement = connection.createStatement();
			//String sql = "INSERT goods(name,price,desp) VALUES('耳机',200.0,'蓝牙耳机')";
//			int count = statement.executeUpdate(sql);
//			System.out.println("count:"+count);
//			if (count > 0) {
//				System.out.println("保存成功");
//			}
//			String sql = "UPDATE goods SET name='mac' WHERE id=5";
//			String sql = "DELETE FROM goods WHERE id=6";
//			int count = statement.executeUpdate(sql);
//			if (count > 0) {
//				System.out.println("删除数据成功");
//			}
			
			String sql = "SELECT * FROM goods";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String desp = resultSet.getString("desp");
				float price = resultSet.getFloat("price");
				System.out.println(id+"   "+name+"    "+price+"   "+desp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				connection = null;
			}
			if (statement !=  null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				statement = null;
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resultSet = null;
			}
		}
	}
}
