package com.prim.lucenedemo.service;

import com.prim.lucenedemo.pojo.JobInfo;

import java.util.List;

/**
 * @program: lucene-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-16 14:51
 * @PackageName: com.prim.lucenedemo.service
 * @ClassName: JobInfoService.java
 **/
public interface JobInfoService {
    JobInfo selectById(Long id);

    List<JobInfo> selectAll();
}
