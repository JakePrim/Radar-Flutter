package com.prim.oa.controller;

import com.prim.oa.biz.DepartmentBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author prim
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentBiz departmentBiz;

    @GetMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", departmentBiz.getAll());
        return "department_list";
    }
}
