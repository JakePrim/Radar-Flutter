package com.sufulu.entity;

import java.io.Serializable;

/**
 * 用户实体类
 */
public class User implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String phone;
    private String createtime;

    public User() {
    }

    public User(Integer uid, String username, String password, String phone, String createtime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.createtime = createtime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
