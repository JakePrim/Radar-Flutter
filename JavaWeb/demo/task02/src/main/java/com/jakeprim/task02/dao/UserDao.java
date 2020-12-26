package com.jakeprim.task02.dao;

import com.jakeprim.task02.model.User;
import com.jakeprim.task02.utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public int createUser(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        int row = 0;
        try {
            connection = DbUtils.getConnection();
            String sql = "insert into t_user values(null,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            row = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DbUtils.close(connection, ps);//关闭资源
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return row;
    }
}
