package com.prim.test.json;

import com.alibaba.fastjson.JSON;
import com.jakeprim.utils.DateUtils;

import java.util.ArrayList;

public class Test {
    @org.junit.Test
    public void javaToJson(){
        Person person = new Person("xiaobing",25, DateUtils.getDateFormart());
        String json = JSON.toJSONString(person);
        System.out.println(json);
    }

    @org.junit.Test
    public void javaListToJson(){
        Person person = new Person("xiaobing1",25, DateUtils.getDateFormart());
        Person person1 = new Person("xiaobing2",26, DateUtils.getDateFormart());
        Person person2 = new Person("xiaobing3",27, DateUtils.getDateFormart());
        ArrayList<Person> list = new ArrayList<>();
        list.add(person);
        list.add(person1);
        list.add(person2);
        String json = JSON.toJSONString(list);
        System.out.println(json);
    }
}
