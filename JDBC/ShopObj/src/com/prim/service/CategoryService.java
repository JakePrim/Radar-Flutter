package com.prim.service;

import java.util.List;

import com.prim.domain.Category;

public interface CategoryService {

	List<Category> findAll();

	boolean save(Category categroy);

	Category findOnce(int cid);

	boolean update(int cid, Category category);

	boolean delete(int cid);

}
