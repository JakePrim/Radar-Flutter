package com.prim.dao;

import java.sql.Connection;
import java.util.List;

import com.prim.domain.Category;

public interface CategoryDao {

	List<Category> findAll();

	boolean save(Category categroy);

	Category findOnce(int cid);

	boolean update(int cid, Category category);

	boolean delete(int cid);

	boolean delete(Connection connection, int cid);

}
