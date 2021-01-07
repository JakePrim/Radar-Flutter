package com.jakeprim.service;

import java.util.ArrayList;
import java.util.List;

import com.jakeprim.domain.Category;

public interface CategoryService {
	//模拟数据库存储
	public static final List<Category> categoryDb = new ArrayList<Category>();
	public void addCatgory(String categoryId, String catgoryName);
	public void deleteCatgory(String categoryId);
}
