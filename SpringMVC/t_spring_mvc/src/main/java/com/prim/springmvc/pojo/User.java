package com.prim.springmvc.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User {
    private String username;
    private Long password;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getPassword() {
        return password;
    }

    public void setPassword(Long password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password=" + password +
                ", time=" + time +
                '}';
    }
}
