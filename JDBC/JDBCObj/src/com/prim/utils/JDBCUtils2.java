package com.prim.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * jdbc 工具类
 * @author prim
 *
 */
public class JDBCUtils2 {
	private static final ComboPooledDataSource source  = new ComboPooledDataSource();
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection connection = source.getConnection();
		return connection;
	}
	
	public static void close(Connection connection,Statement statement) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			connection = null;
		}
		
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			statement = null;
		}
	}
	
	public static void close(Connection connection,Statement statement,ResultSet resultSet) {
		close(connection, statement);
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultSet= null;
		}
	}
	
	
	/**
	 * true 表示更新成功 false 表示更新失败
	 * @param sql
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static boolean executeUpdate(String sql) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		int count = statement.executeUpdate(sql);
		close(connection, statement);
		return count > 0;
	}	
}
