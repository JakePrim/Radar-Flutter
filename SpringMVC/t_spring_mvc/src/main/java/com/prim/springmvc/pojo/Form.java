package com.prim.springmvc.pojo;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class Form {
    private String name;
    private String course;
    private List<Integer> purpose;
    private IDCard idCard;

    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Integer> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<Integer> purpose) {
        this.purpose = purpose;
    }
}
