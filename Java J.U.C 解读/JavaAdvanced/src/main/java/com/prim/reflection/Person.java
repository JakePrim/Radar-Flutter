package com.prim.reflection;

public class Person {
    public String name;
    private String sex;

    public String eat;

    public Person() {
    }

    public Person(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    private Person(String name){
        this.name = name;
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

    public void eat(){
        System.out.println("eat.....");
    }

    private void run(){
        System.out.println("跑........");
    }

    private void runs(String name){
        System.out.println("name = " + name +" 快跑。。。。。。");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
