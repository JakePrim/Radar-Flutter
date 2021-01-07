package com.example.task04.dao;

import com.example.task04.model.User;
import com.example.task04.util.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImp implements UserDao {
    @Override
    public User userLogin(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DbUtils.getConnection();
            String sql = "select * from t_user where username=? and password=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                User user1 = new User();
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                user1.setUsername(username);
                user1.setPassword(password);
                user1.setId(resultSet.getInt("id"));
                return user1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection, ps, resultSet);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
