package com.prim.lucenedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prim.lucenedemo.mapper.JobInfoMapper;
import com.prim.lucenedemo.pojo.JobInfo;
import com.prim.lucenedemo.service.JobInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: lucene-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-16 14:52
 * @PackageName: com.prim.lucenedemo.service.impl
 * @ClassName: JobInfoServiceImpl.java
 **/
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoMapper jobInfoMapper;

    @Override
    public JobInfo selectById(Long id) {
        return jobInfoMapper.selectById(id);
    }

    @Override
    public List<JobInfo> selectAll() {
        QueryWrapper<JobInfo> queryWrapper = new QueryWrapper<>();
        return jobInfoMapper.selectList(queryWrapper);
    }
}
