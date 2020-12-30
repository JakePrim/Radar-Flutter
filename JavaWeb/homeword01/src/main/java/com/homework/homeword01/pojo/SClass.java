package com.homework.homeword01.pojo;

/**
 * 班级信息
 */
public class SClass {
    private int id;
    private String name;
    private String grade;
    private String teacher;
    private String slogan;
    private int num;

    public SClass() {
    }

    public SClass(String name, String grade, String teacher, String slogan, int num) {
        this.name = name;
        this.grade = grade;
        this.teacher = teacher;
        this.slogan = slogan;
        this.num = num;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "SClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", teacher='" + teacher + '\'' +
                ", slogan='" + slogan + '\'' +
                ", num=" + num +
                '}';
    }
}
