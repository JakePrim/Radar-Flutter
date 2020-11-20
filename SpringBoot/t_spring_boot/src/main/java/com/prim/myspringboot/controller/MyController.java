package com.prim.myspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring MVC 的 controller
 *
 * @author prim
 */
@Controller
public class MyController {
    @RequestMapping("/out")
    @ResponseBody
    public String out() {
        return "success";
    }
}
