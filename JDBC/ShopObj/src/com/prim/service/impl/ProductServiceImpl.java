package com.prim.service.impl;

import java.sql.Connection;
import java.util.List;

import com.prim.dao.ProductDao;
import com.prim.dao.impl.ProductDaoImpl;
import com.prim.domain.PageBean;
import com.prim.domain.Product;
import com.prim.service.ProductService;

public class ProductServiceImpl implements ProductService {

	private ProductDao productDao;
	
	public ProductServiceImpl() {
		super();
		productDao = new ProductDaoImpl();
	}

	@Override
	public List<Product> findAll() {
		
		return productDao.findAll();
	}

	@Override
	public boolean save(Product product) {
		return productDao.save(product);
	}

	@Override
	public boolean delete(int pid) {
		return productDao.delete(pid);
	}

	@Override
	public Product findOnce(int pid) {
		return productDao.findOnce(pid);
	}

	@Override
	public boolean update(Product product, int pid) {
		// TODO Auto-generated method stub
		return productDao.update(product,pid);
	}

	@Override
	public List<Product> findByCid(int cid) {
		// TODO Auto-generated method stub
		return productDao.findByCid(cid);
	}

	@Override
	public void update(Connection connection, Product product, int id) {
		productDao.update(connection, product,id);
	}

	@Override
	public PageBean<Product> findByPage(int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		pageBean.setPage(page);
		int limit = 6;
		pageBean.setLimit(limit);
		int totalCount = productDao.findByCount();
		int totalPage = 0;
		if (totalCount % limit ==0) {
			totalPage = (totalCount / limit);
		}else {
			totalPage = (totalCount / limit) + 1;
		}
		pageBean.setTotalPage(totalPage);
		pageBean.setTotalCount(totalCount);
		System.out.println(pageBean);
		int begin = (page - 1) * limit;
		List<Product> products = productDao.findLimit(begin,limit);
		pageBean.setLists(products);
		
		return pageBean;
	}

}
