package com.prim.servlet;

import java.sql.Date;

public class Course {
	private String name;
	private String category;
	private String desp;
	private Date createtime;
	
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	@Override
	public String toString() {
		return "Course [name=" + name + ", category=" + category + ", desp=" + desp + "]";
	}
	
	
}
