package com.prim.springbootdata.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-28 20:58
 * @PackageName: com.prim.springbootdata.pojo
 * @ClassName: Student.java
 **/
@Component
public class Student {
    @Value("${person.id}")
    private int number;
    @Value("${person.name}")
    private String name;
//    @Value("${person.hobby}")
    private List hobby;
//    @Value("${person.map}")
    private Map ss;

    @Override
    public String toString() {
        return "Student{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", hobby=" + hobby +
                ", ss=" + ss +
                '}';
    }

    public void setHobby(List hobby) {
        this.hobby = hobby;
    }

    public void setSs(Map ss) {
        this.ss = ss;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
