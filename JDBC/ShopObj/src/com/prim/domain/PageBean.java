package com.prim.domain;

import java.util.List;

public class PageBean<T> {
	private int page;
	private int limit;
	private int totalPage;
	private int totalCount;
	private List<T> lists;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	@Override
	public String toString() {
		return "PageBean [page=" + page + ", limit=" + limit + ", totalPage=" + totalPage + ", totalCount=" + totalCount
				+ ", lists=" + lists + "]";
	}
	
	
}
