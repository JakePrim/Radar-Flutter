package com.jakeprim.jstl;

public class Notice {

	private String id;
	private String name;

	public Notice(String id, String name) {
          this.id = id;
          this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", name=" + name + "]";
	}
	
	

}
