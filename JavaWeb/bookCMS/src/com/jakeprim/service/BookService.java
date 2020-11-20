package com.jakeprim.service;

import java.util.ArrayList;
import java.util.List;

import com.jakeprim.domain.Book;
import com.jakeprim.domain.Category;

public interface BookService {
	//模拟数据库存储
	public static final List<Book> bookDb = new ArrayList<Book>();
	public void addBook(Book book);
	public void updateBook(Book book);
	public void deteleBook(String bookId);
	public Book getBooksByCondition(String bookID);
	public List<Book> getBooksByCategory(String catgoryName);
}
