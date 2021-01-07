package com.prim.utils;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Java 连接数据库注意导入的数据库驱动,如果安装的为5.0则使用5.0的数据库驱动
 * 如果为8.0的版本一定要使用8.0的数据库驱动否则出现错误.一定注意
 * @author prim
 *
 */
public class JDBCUtils {
	
	private static final ComboPooledDataSource source  = new ComboPooledDataSource();
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection connection = source.getConnection();
		return connection;
	}
	
	public static ComboPooledDataSource getDataSource() {
		return source;
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
}
