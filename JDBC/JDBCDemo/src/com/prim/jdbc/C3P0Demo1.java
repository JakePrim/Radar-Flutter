package com.prim.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Demo1 {
	public static void main(String[] args) {
		demo1();
	}
	
	public static void demo1() {
		Connection connection = null;
		PreparedStatement statement= null;
		ResultSet resultSet = null;
		try {
			//从连接池获得连接
			connection = JDBCUtils2.getConnection();
			statement = connection.prepareStatement("SELECT * FROM user2");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id")+"   "+resultSet.getString("username")+
						"   "+resultSet.getString("password"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
	}
}
