package com.prim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController  //相当于 @Controller 和 @ResponseBody
@RequestMapping("/restful")
public class RestfulController {
    /**
     * 根据id进行查询
     *
     * @return
     */
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @GetMapping("/user/{id}") //和上述的RequestMapping的配置一致
    public String findById(@PathVariable Integer id) {
        //调用service根据id查询
        return "findById: " + id;
    }

    /**
     * 新增操作
     *
     * @return
     */
    @PostMapping("/user")
    public String post() {
        //调用service的新增方法
        return "post";
    }

    /**
     * 更新方法
     */
    @PutMapping("/user/{id}")
    public String put(@PathVariable Integer id) {
        return "put";
    }

    /**
     * 删除方法
     */
    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable Integer id) {
        return "delete";
    }
}
