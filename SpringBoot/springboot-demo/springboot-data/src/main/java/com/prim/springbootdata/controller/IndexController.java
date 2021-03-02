package com.prim.springbootdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-02 07:12
 * @PackageName: com.prim.springbootdata.controller
 * @ClassName: IndexController.java
 **/
@Controller
public class IndexController {
    @GetMapping("toIndex")
    public String toIndex(Model model) {
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "index";
    }
}
