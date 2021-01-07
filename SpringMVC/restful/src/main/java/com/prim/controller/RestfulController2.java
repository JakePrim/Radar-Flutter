package com.prim.controller;

import com.prim.controller.pojo.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author prim
 */
@RestController //Restful风格的注解 不用在每个方法上写@ResponseBody了
@RequestMapping("/restful2")
//@CrossOrigin(origins = {"http://localhost:8081"})//服务的提供端 设置哪些域名可以被访问
//@CrossOrigin(origins = "*",maxAge = 3600)//不推荐使用origins = "*" 这代表了所有地址 端口 https http 都允许访问controller中的
//每一个URL 这有重大的安全隐患 实际开发中一定不能使用。
//maxAge = 3600 设置预检请求的缓存时间 put delete 非简单请求会发送一个预检请求。每一次是有两个请求会增加服务器的压力
//maxAge 参数可以将预检请求的结果进行缓存 在小时的时间内 同样的put请求就不进行预检请求处理
public class RestfulController2 {
    ///restful2/t/{?} 放在uri中的变量成为路径变量 这样使用很普遍的
    //POST /restful2/t/100 如何获取到100呢？
    //通过@XXXMapping(/xx/{id}) 注入路径变量
    //通过@PathVariable 注入给参数
    @PostMapping("/t/{rid}")
    public String doPostRequest(@PathVariable("rid") Integer requestId, Person person) {
        System.out.println(person.getName() + ":" + person.getAge());
        return "{\"message\":\"This is Message POST请求 增加操作\",\"id\":" + requestId + "}";
    }

    /**
     * 交给jackson自动调整
     *
     * @param id
     * @return
     */
    @GetMapping("/person")
    public Person findByPersonId(Integer id) {
        Person person = new Person();
        if (id == 1) {
            person.setName("lily");
            person.setAge(23);
        } else {
            person.setName("smith");
            person.setAge(22);
        }
        return person;
    }

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
