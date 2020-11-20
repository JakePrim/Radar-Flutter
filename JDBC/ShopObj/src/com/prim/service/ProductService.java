package com.prim.service;

import java.sql.Connection;
import java.util.List;

import com.prim.domain.PageBean;
import com.prim.domain.Product;

public interface ProductService {

	List<Product> findAll();

	boolean save(Product product);

	boolean delete(int pid);

	Product findOnce(int pid);

	boolean update(Product product, int pid);

	List<Product> findByCid(int cid);

	void update(Connection connection, Product product, int id);

	PageBean<Product> findByPage(int page);

}
