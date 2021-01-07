package com.prim.controller;

import com.prim.controller.pojo.Person;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author prim
 */
@Controller
@RequestMapping("/restful")
public class RestfulController {

    //------------------------------ 基本的使用 -----------------------------------------//
    @GetMapping("/t")
    @ResponseBody
    public String test() {
        return "{\"message\":\"This is Message 中文\"}";
    }

    @PostMapping("/t")
    @ResponseBody
    public String doPostRequest() {
        return "{\"message\":\"This is Message POST请求 增加操作\"}";
    }

    /**
     * put和delete 非简单请求
     * @param person
     * @return
     */
    @PutMapping("/t")
    @ResponseBody
    public String doPutRequest(Person person) {
        System.out.println(person.getName()+":"+person.getAge());
        return "{\"message\":\"This is Message PUT请求 更新操作\"}";
    }

    @DeleteMapping("/t")
    @ResponseBody
    public String doDeleteRequest() {
        return "{\"message\":\"This is Message DELETE请求 删除操作\"}";
    }
}
