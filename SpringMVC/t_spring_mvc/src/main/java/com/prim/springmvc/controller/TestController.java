package com.prim.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author prim
 */
@Controller
public class TestController {
    @GetMapping("/t")//get方式请求等url localhost/t
    @ResponseBody //直接向响应输出字符串数据，不跳转页面
    public String test() {
        return "hello spring mvc success";
    }
}
