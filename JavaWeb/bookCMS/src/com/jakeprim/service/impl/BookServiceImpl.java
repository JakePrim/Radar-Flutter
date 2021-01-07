package com.jakeprim.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jakeprim.domain.Book;
import com.jakeprim.service.BookService;

public class BookServiceImpl implements BookService {

	@Override
	public void addBook(Book book) {
		//如果存在相同的ID 则删除之前的数据
		deteleBook(book.getBookId());
		bookDb.add(book);
	}

	@Override
	public void updateBook(Book book) {
		for (Book item : bookDb) {
			if (item.getBookId().equals(book.getBookId())) {
				item.setBookName(book.getBookName());
				item.setCategory(book.getCategory());
				item.setNote(book.getNote());
				item.setPath(book.getPath());
				item.setPrice(book.getPrice());
				break;
			}
		}
	}

	@Override
	public void deteleBook(String bookId) {
		for (Book book : bookDb) {
			if (book.getBookId().equals(bookId)) {
				bookDb.remove(book);
				break;
			}
		}
	}

	@Override
	public Book getBooksByCondition(String bookID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> getBooksByCategory(String catgoryName) {
		if (catgoryName == null || catgoryName == "") {
			return bookDb;
		}
		List<Book> newBooks = new ArrayList<Book>();
		for (Book book : bookDb) {
			if (book.getCategory().getCategoryName().equals(catgoryName)) {
				newBooks.add(book);
			}
		}
		return newBooks;
	}

}
