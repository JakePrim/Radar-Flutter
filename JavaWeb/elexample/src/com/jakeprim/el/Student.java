package com.jakeprim.el;

public class Student {

	private String name;
	private String moblie;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", moblie=" + moblie + "]";
	}
	
	
}
