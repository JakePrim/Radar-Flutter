package com.prim.entity;

/**
 * id INT PRIMARY KEY AUTO_INCREMENT ,
 * NAME VARCHAR (30),		-- 员工姓名
 * age DOUBLE ,				-- 员工年龄
 * sex VARCHAR (6),			-- 员工性别
 * salary DOUBLE ,			-- 薪水
 * empdate DATE ,			-- 入职日期
 * did INT,
 */
public class Employee {
    private int id;
    private String name;
    private double age;
    private String sex;
    private double salary;
    private String empdate;
    private int did;

    private Dept dept;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmpdate() {
        return empdate;
    }

    public void setEmpdate(String empdate) {
        this.empdate = empdate;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", salary=" + salary +
                ", empdate='" + empdate + '\'' +
                ", did=" + did +
                ", dept=" + dept +
                '}';
    }
}
