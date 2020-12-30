package com.jakeprim.demo02.model;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

public class Student implements Serializable, HttpSessionActivationListener {
    private String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        //从硬盘将session数据读取到内存中 需要添加配置信息
        System.out.println("执行了活化操作...");
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        //停止服务器 将session数据存放到硬盘中
        System.out.println("执行了钝化操作："+se.getSession());
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
