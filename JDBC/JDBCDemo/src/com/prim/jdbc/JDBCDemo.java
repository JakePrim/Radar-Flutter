package com.prim.jdbc;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class JDBCDemo {

	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		ResultSet reuslt = null;
		Connection conn = null;
		Statement statement = null;
		try {
			//1 加载驱动
			//DriverManager 类主要功能包括:注册驱动和获取数据库连接
//			DriverManager.registerDriver(new Driver());//会导致驱动注册两次 Driver中的静态块会再次注册一次
			Class.forName("com.mysql.jdbc.Driver");// 实际开发中的注册驱动方式
			//2 获得连接
			//jdbc:mysql://主机名:端口号/数据库
			 conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/king"
			+"?useSSL=false&serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf-8"
					, "root", "root");
			//jdbc :协议
			//mysql:子协议
			//localhost:主机名
			//3306:端口号
			//url可以简写:如果连接的是本机的话可以写成:jdbc:mysql:///db_name
			
			//Connection - 连接对象
			
			//3 创建SQL语句对象
			String sql = "SELECT * FROM user";
			//创建Statement执行SQL语句
			//创建执行SQL语句的对象
			 statement = conn.createStatement();
			//createStatement - Statement         : 执行SQL语句 会有SQL注入的存在漏洞
			//prepareStatement - PreparedStatement: 预编译SQL语句
			//prepareCall - CallableStatement     : 执行SQL中存储过程
			
			//Connection 还可以进行事务的管理
			//setAutoCommit     -      设置事务是否自动提交
			//commit - 事务提交
			//rollback - 事务回滚
			
			//Statement API 主要用来执行SQL语句
			//boolean execute() 执行SQL 执行select语句返回true,否则返回false
			//ResultSet executeQuery() 执行select语句
			//int executeUpdate() 执行insert/update/delete
			
			//执行批处理操作
			//addBatch(sql)        添加到批处理
			//executeBatch(sql)    执行批处理
			//clearBatch()         清空批处理
			 reuslt =statement.executeQuery(sql);// 执行SQL语句
			
			//ResultSet : 结果集
			//select语句所查询结果的封装
			while (reuslt.next()) {
				int id = reuslt.getInt("id");
				String username = reuslt.getString("username");
				String email = reuslt.getString("email");
				int age = reuslt.getInt("age");
				System.out.println("id:"+id+" username:"+username+" email:"+email+" age:"+age);
			}
			//4 释放资源 运行完之后必须将与数据交互的进行资源释放
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 在finally 释放资源
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
			if (reuslt != null) {
				try {
					reuslt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reuslt = null;
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
	}
}
