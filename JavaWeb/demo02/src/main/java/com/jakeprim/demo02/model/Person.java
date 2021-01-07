package com.jakeprim.demo02.model;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class Person implements HttpSessionBindingListener {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("对象绑定到session中了:"+event.getName());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("对象解除绑定成功:"+event.getName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
