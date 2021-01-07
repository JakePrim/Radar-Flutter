package com.prim.domain;

public class Product {
	private int id;
	private Category category = new Category();
	private String pname;
	private String author;
	private Double price;
	private String desc;
	private String filename;
	private String path;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", pname=" + pname + ", author=" + author + ", price=" + price
				+ ", desc=" + desc + ", filename=" + filename + ", path=" + path + "]";
	}
	
	
}
