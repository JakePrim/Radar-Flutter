package com.prim.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.prim.dao.UserDao;
import com.prim.domain.User;
import com.prim.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User login(User user) {
		if (user == null) {
			return null;
		}
		//连接数据库
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User loginUser = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
			System.out.println("DAO :"+user.getUsername()+" "+user.getPassword());
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				loginUser = new User();
				loginUser.setId(resultSet.getInt("id"));
				loginUser.setUsername(resultSet.getString("username"));
				//不应该将用户密码存储到session中
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return loginUser;
	}

}
