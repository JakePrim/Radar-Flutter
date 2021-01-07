package com.prim.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo2 {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/king", "root", "root");
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM goods WHERE price < 3500";
			ResultSet set = statement.executeQuery(sql);
			while (set.next()) {
				int id = set.getInt("id");
				String name = set.getString("name");
				float price = set.getFloat("price");
				String desp = set.getString("desp");
				System.out.println(id+"   "+name+"   "+price+"   "+desp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
