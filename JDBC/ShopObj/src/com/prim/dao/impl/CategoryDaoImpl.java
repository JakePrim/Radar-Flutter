package com.prim.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.prim.dao.CategoryDao;
import com.prim.domain.Category;
import com.prim.utils.JDBCUtils;
import com.sun.org.apache.regexp.internal.recompile;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet= null;
		List<Category> categories = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT * FROM category");
			resultSet = statement.executeQuery();
			categories = new ArrayList<Category>();
			while (resultSet.next()) {
				Category category = new Category();
				category.setId(resultSet.getInt("id"));
				category.setCname(resultSet.getString("cname"));
				category.setCdesc(resultSet.getString("cdesc"));
				categories.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return categories;
	}

	@Override
	public boolean save(Category categroy) {
		Connection connection = null;
		PreparedStatement statement = null;
		boolean isSave = false;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("INSERT category(cname,cdesc) VALUES(?,?)");
			statement.setString(1, categroy.getCname());
			statement.setString(2, categroy.getCdesc());
			int num = statement.executeUpdate();
			isSave = num > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(connection, statement);
		}
		return isSave;
	}

	@Override
	public Category findOnce(int cid) {
		Category category = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT * FROM category WHERE id=?");
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				category = new Category();
				category.setId(resultSet.getInt("id"));
				category.setCname(resultSet.getString("cname"));
				category.setCdesc(resultSet.getString("cdesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return category;
	}

	@Override
	public boolean update(int cid, Category category) {
		boolean isUpdate = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("UPDATE category SET cname=?,cdesc=? WHERE id=?");
			statement.setString(1, category.getCname());
			statement.setString(2, category.getCdesc());
			statement.setInt(3, cid);
			int num = statement.executeUpdate();
			isUpdate = num > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(connection, statement);
		}
		return isUpdate;
	}

	@Override
	public boolean delete(int cid) {
		boolean isUpdate = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("DELETE FROM category WHERE id=?");
			statement.setInt(1, cid);
			int num = statement.executeUpdate();
			isUpdate = num > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(connection, statement);
		}
		return isUpdate;
	}

	@Override
	public boolean delete(Connection connection, int cid) {
		boolean isUpdate = false;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("DELETE FROM category WHERE id=?");
			statement.setInt(1, cid);
			int num = statement.executeUpdate();
			isUpdate = num > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return isUpdate;
	}

}
