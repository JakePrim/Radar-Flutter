package io_demo;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 2496520801365497998L;
    private String name;
    private transient String password;//密码不参与序列化
    private String phone;

    public User() {
    }

    public User(String name, String password, String phone) {
        this.name = name;
        this.password = password;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
