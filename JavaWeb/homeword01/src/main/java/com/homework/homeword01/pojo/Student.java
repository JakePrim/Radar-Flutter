package com.homework.homeword01.pojo;

import java.util.Date;

/**
 * 学生的bean类
 */
public class Student {
    private int id;
    private String name;
    private int sex;//性别使用0和1来代替，节省空间 0 女生   1：男生
    private String brithday;
    private String desc;
    private String email;
    private int cid;//班级id

    private SClass sClass;//具体的班级信息

    public Student() {
    }

    public Student(String name, int sex, String brithday, String desc, String email) {
        this.name = name;
        this.sex = sex;
        this.brithday = brithday;
        this.desc = desc;
        this.email = email;
    }

    public Student(String name, int sex, String brithday, String desc, String email,int cid) {
        this.name = name;
        this.sex = sex;
        this.brithday = brithday;
        this.desc = desc;
        this.email = email;
        this.cid = cid;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public SClass getsClass() {
        return sClass;
    }

    public void setsClass(SClass sClass) {
        this.sClass = sClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", brithday=" + brithday +
                ", desc='" + desc + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
