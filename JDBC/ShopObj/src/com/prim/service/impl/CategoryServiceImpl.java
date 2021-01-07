package com.prim.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.prim.dao.CategoryDao;
import com.prim.dao.impl.CategoryDaoImpl;
import com.prim.domain.Category;
import com.prim.domain.Product;
import com.prim.service.CategoryService;
import com.prim.service.ProductService;
import com.prim.utils.JDBCUtils;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findAll();
	}

	@Override
	public boolean save(Category categroy) {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.save(categroy);
	}

	@Override
	public Category findOnce(int cid) {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findOnce(cid);
	}

	@Override
	public boolean update(int cid, Category category) {
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.update(cid, category);
	}

	@Override
	public boolean delete(int cid) {
		boolean isDelete =false;
		//下面的操作中,每次操作都获取了一个连接,如果分类删除异常而商品表中的cid已经置为空,数据就会出现问题
		//使用同一个连接,手动提交事务,出现异常回滚事务
		Connection connection=null;
		try {
			connection = JDBCUtils.getConnection();//获取一个连接 修改和删除操作使用同一个连接
			connection.setAutoCommit(false);//设置为手动提交
			// 将属于的商品的分类置为null
			ProductService productService = new ProductServiceImpl();
			List<Product> products = productService.findByCid(cid);//查找数据不用使用同一个连接
			for (Product product : products) {
				if (product != null) {
					product.getCategory().setId(null);
					productService.update(connection,product, product.getId());
				}
			}
			CategoryDao categoryDao = new CategoryDaoImpl();
			isDelete = categoryDao.delete(connection,cid);
		} catch (Exception e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//回滚事务
			}
			e.printStackTrace();
		}finally {
			if (connection != null) {
				try {
					connection.commit();
					connection.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}				
			}
		}
		return isDelete;
	}
}
