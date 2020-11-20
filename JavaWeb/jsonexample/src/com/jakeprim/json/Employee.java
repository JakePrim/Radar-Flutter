package com.jakeprim.json;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Employee {
	
	private Integer empno;
	
	private String ename;
	
	private String job;
	
	@JSONField(format = "yyyy-MM-dd HH:mm:ss",name = "hiredate")//FastJson 提供了对日期的格式化处理 还可以设置其他的名字
	private Date date;
	
	@JSONField(serialize = false) // serialize 表示不进行序列化
	private String dname;
	
	private Double salary;
	
	public Integer getEmpno() {
		return empno;
	}
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", ename=" + ename + ", job=" + job + ", date=" + date + ", dname=" + dname
				+ ", salary=" + salary + "]";
	}
	
	
	
	
}
