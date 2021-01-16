package com.prim.dao.impl;

import com.prim.dao.UserDao;

import java.util.*;

public class UserDaoImpl implements UserDao {
    private String username;
    private Integer age;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private List<Object> list;

    public void setList(List<Object> list) {
        this.list = list;
    }

    private Set<Object> set;

    public void setSet(Set<Object> set) {
        this.set = set;
    }

    private Object[] array;

    public void setArray(Object[] array) {
        this.array = array;
    }

    private Map<String, Object> map;

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void save() {
        System.out.println(properties);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        System.out.println(Arrays.asList(array));
        System.out.println(set);
        System.out.println(list);
        System.out.println(username + ":" + age);
        System.out.println("保存成功");
    }
}
