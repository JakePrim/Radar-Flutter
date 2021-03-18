package com.prim.lucenedemo.controller;

import com.prim.lucenedemo.pojo.JobInfo;
import com.prim.lucenedemo.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: lucene-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-16 14:56
 * @PackageName: com.prim.lucenedemo.controller
 * @ClassName: JobInfoController.java
 **/
@RestController
@RequestMapping("/jobInfo")
public class JobInfoController {

    @Autowired
    private JobInfoService jobInfoService;

    @GetMapping("/query/{id}")
    public JobInfo selectById(@PathVariable Long id) {
        return jobInfoService.selectById(id);
    }

    @GetMapping("/query")
    public List<JobInfo> selectById() {
        return jobInfoService.selectAll();
    }

}
