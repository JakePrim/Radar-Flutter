package com.prim.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcDemo4 {
	public static void main(String[] args) {
		try {
			//插入操作
			boolean isUpdate = JDBCUtils.executeUpdate("INSERT goods(name,price,desp) VALUES('手机',2500.0,'12223'),('冰箱',1500.0,'sd'),('洗衣机',3000.0,'sd'),"
					+ "('耳机',200.0,'erji')");
			if (isUpdate) {
				System.out.println("插入操作成功");
			}
			//查询操作
			Connection connection = JDBCUtils.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM goods");
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id")+
						"   "+resultSet.getString("name") +"   "+resultSet.getFloat("price")
						+"   "+resultSet.getString("desp"));
			}
			JDBCUtils.close(connection, statement, resultSet);
			//修改操作
			isUpdate = JDBCUtils.executeUpdate("UPDATE goods SET price=5000 WHERE name='手机'");
			if (isUpdate) {
				System.out.println("更新操作成功");
			}
			//删除操作
			boolean isDelete = JDBCUtils.executeUpdate("DELETE FROM goods WHERE name='洗衣机'");
			if (isDelete) {
				System.out.println("删除操作成功");
			}
			//升序排序显示
			Connection connection1 = JDBCUtils.getConnection();
			Statement statement1 = connection1.createStatement();
			ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM goods ORDER BY price ASC");
			while (resultSet1.next()) {
				System.out.println(resultSet1.getInt("id")+
						"   "+resultSet1.getString("name") +"   "+resultSet1.getFloat("price")
						+"   "+resultSet1.getString("desp"));
			}
			JDBCUtils.close(connection1, statement1, resultSet1);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
