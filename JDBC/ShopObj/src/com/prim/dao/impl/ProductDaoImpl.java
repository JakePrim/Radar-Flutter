package com.prim.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.storeconfig.StandardHostSF;

import com.prim.dao.ProductDao;
import com.prim.domain.Product;
import com.prim.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findAll() {
		List<Product> products = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT p.id,p.pname,p.desc,p.filename,p.path,p.cid,p.author,p.price,c.cname,c.cdesc" + 
					" FROM product AS p JOIN category AS c ON p.cid = c.id ORDER BY p.id DESC");
			resultSet = statement.executeQuery();
			products = new ArrayList<Product>();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setPname(resultSet.getString("pname"));
				product.setDesc(resultSet.getString("desc"));
				product.setFilename(resultSet.getString("filename"));
				product.setPath(resultSet.getString("path"));
				product.setAuthor(resultSet.getString("author"));
				product.setPrice(resultSet.getDouble("price"));
				product.getCategory().setId(resultSet.getInt("cid"));
				product.getCategory().setCname(resultSet.getString("cname"));
				product.getCategory().setCdesc(resultSet.getString("cdesc"));
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return products;
	}

	@Override
	public boolean save(Product product) {
		boolean isSave = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("INSERT product(pname,price,`desc`,filename,path,cid,author) VALUES(?,?,?,?,?,?,?)");
			statement.setString(1, product.getPname());
			statement.setDouble(2, product.getPrice());
			statement.setString(3, product.getDesc());
			statement.setString(4, product.getFilename());
			statement.setString(5, product.getPath());
			statement.setInt(6, product.getCategory().getId());
			statement.setString(7, product.getAuthor());
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
	public boolean delete(int pid) {
		boolean isSave = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("DELETE FROM product WHERE id=?");
			statement.setInt(1, pid);
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
	public Product findOnce(int pid) {
		Product product = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT p.id,p.pname,p.desc,p.filename,p.path,p.cid,p.author,p.price,c.cname,c.cdesc" + 
					" FROM product AS p JOIN category AS c ON p.cid = c.id WHERE p.id=?");
			statement.setInt(1, pid);
			resultSet = statement.executeQuery();
			product = new Product();
			if (resultSet.next()) {
				product.setId(resultSet.getInt("id"));
				product.setPname(resultSet.getString("pname"));
				product.setDesc(resultSet.getString("desc"));
				product.setFilename(resultSet.getString("filename"));
				product.setPath(resultSet.getString("path"));
				product.setPrice(resultSet.getDouble("price"));
				product.setAuthor(resultSet.getString("author"));
				product.getCategory().setId(resultSet.getInt("cid"));
				product.getCategory().setCname(resultSet.getString("cname"));
				product.getCategory().setCdesc(resultSet.getString("cdesc"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(connection, statement,resultSet);
		}
		return product;
	}

	@Override
	public boolean update(Product product, int pid) {
		boolean isSave = false;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("UPDATE product SET pname=?,price=?,`desc`=?,filename=?,path=?,cid=?,author=? WHERE id=?");
			statement.setString(1, product.getPname());
			statement.setDouble(2, product.getPrice());
			statement.setString(3, product.getDesc());
			statement.setString(4, product.getFilename());
			statement.setString(5, product.getPath());
			statement.setObject(6, product.getCategory().getId());
			statement.setString(7, product.getAuthor());
			statement.setInt(8, pid);
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
	public List<Product> findByCid(int cid) {
		List<Product> products = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT p.id,p.pname,p.desc,p.filename,p.path,p.cid,p.author,p.price,c.cname,c.cdesc" + 
					" FROM product AS p JOIN category AS c ON p.cid = c.id WHERE p.cid=? ORDER BY p.id DESC");
			statement.setInt(1, cid);
			resultSet = statement.executeQuery();
			products = new ArrayList<Product>();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setPname(resultSet.getString("pname"));
				product.setDesc(resultSet.getString("desc"));
				product.setFilename(resultSet.getString("filename"));
				product.setPath(resultSet.getString("path"));
				product.setAuthor(resultSet.getString("author"));
				product.setPrice(resultSet.getDouble("price"));
				product.getCategory().setId(resultSet.getInt("cid"));
				product.getCategory().setCname(resultSet.getString("cname"));
				product.getCategory().setCdesc(resultSet.getString("cdesc"));
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return products;
	}

	@Override
	public void update(Connection connection, Product product, int id) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("UPDATE product SET pname=?,price=?,`desc`=?,filename=?,path=?,cid=?,author=? WHERE id=?");
			statement.setString(1, product.getPname());
			statement.setDouble(2, product.getPrice());
			statement.setString(3, product.getDesc());
			statement.setString(4, product.getFilename());
			statement.setString(5, product.getPath());
			statement.setObject(6, product.getCategory().getId());
			statement.setString(7, product.getAuthor());
			statement.setInt(8, id);
			statement.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public int findByCount() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM product");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = (int) resultSet.getLong("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return count;
	}

	@Override
	public List<Product> findLimit(int begin, int limit) {
		List<Product> products = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JDBCUtils.getConnection();
			statement = connection.prepareStatement("SELECT p.id,p.pname,p.desc,p.filename,p.path,p.cid,p.author,p.price,c.cname,c.cdesc" + 
					" FROM product AS p JOIN category AS c ON p.cid = c.id LIMIT ?,?");
			statement.setInt(1, begin);
			statement.setInt(2, limit);
			resultSet = statement.executeQuery();
			products = new ArrayList<Product>();
			while (resultSet.next()) {
				Product product = new Product();
				product.setId(resultSet.getInt("id"));
				product.setPname(resultSet.getString("pname"));
				product.setDesc(resultSet.getString("desc"));
				product.setFilename(resultSet.getString("filename"));
				product.setPath(resultSet.getString("path"));
				product.setAuthor(resultSet.getString("author"));
				product.setPrice(resultSet.getDouble("price"));
				product.getCategory().setId(resultSet.getInt("cid"));
				product.getCategory().setCname(resultSet.getString("cname"));
				product.getCategory().setCdesc(resultSet.getString("cdesc"));
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(connection, statement, resultSet);
		}
		return products;
	}

}
