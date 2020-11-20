package com.prim.bean;


import java.util.Date;

/**
 * @author prim
 */
public class Student {
    private int id;
    private String name;
    private String sex;
    private Date birthday;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", born=" + birthday +
                '}';
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBorn() {
        return birthday;
    }

    public void setBorn(Date born) {
        this.birthday = born;
    }
}
