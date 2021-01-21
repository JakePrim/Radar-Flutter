package com.edu.controller;

import com.edu.pojo.Test;
import com.edu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //组合了：@Controller和@ResponseBody
@RequestMapping("/test")
public class TestController {

    @Autowired //注入service
    private TestService testService;

    @RequestMapping("/findAll")
    public List<Test> findAll() {
        List<Test> list = testService.findAll();
        return list;
    }
}
