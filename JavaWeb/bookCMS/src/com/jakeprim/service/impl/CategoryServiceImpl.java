package com.jakeprim.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jakeprim.domain.Category;
import com.jakeprim.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	@Override
	public void addCatgory(String categoryId, String categoryName) {
		//需要注意相同分类ID的替换
		for (Category category : categoryDb) {
			if (category.getCategoryId().equals(categoryId)) {
				category.setCategoryName(categoryName);
				return;
			}
		}
		categoryDb.add(new Category(categoryId, categoryName));
	}

	@Override
	public void deleteCatgory(String categoryId) {
		for (Category category : categoryDb) {
			if (category.getCategoryId().equals(categoryId)) {
				categoryDb.remove(category);
				break;
			}
		}
	}

}
