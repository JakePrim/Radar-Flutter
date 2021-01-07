package com.prim.springmvc.controller;

import com.prim.springmvc.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 整合freemarker模版引擎
 * @author prim
 */
@Controller
@RequestMapping("/fm")
public class FreemarkerController {

    @GetMapping("/test")
    public ModelAndView test() {
        //直接写文件名即可 Spring已经帮助我们找到扩展名
        ModelAndView modelAndView = new ModelAndView("/test");
        User user = new User();
        user.setUsername("andy");
        //传递请求 数据
        modelAndView.addObject("u", user);
        return modelAndView;
    }
}
