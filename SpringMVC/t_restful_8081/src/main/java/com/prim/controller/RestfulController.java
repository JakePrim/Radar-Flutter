package com.prim.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author prim
 */
@RestController
@RequestMapping("restful")
public class RestfulController {
    @GetMapping("persons")
    public List<Person> findPersons() {
        List list = new ArrayList();
        Person p1 = new Person();
        p1.setName("lily");
        p1.setAge(23);
        p1.setBirthday(new Date());
        list.add(p1);
        Person p2 = new Person();
        p2.setName("smith");
        p2.setAge(22);
        p2.setBirthday(new Date());
        list.add(p1);
        return list;
    }
}
