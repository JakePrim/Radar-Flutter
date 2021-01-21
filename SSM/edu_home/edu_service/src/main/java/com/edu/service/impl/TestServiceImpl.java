package com.edu.service.impl;

import com.edu.dao.TestMapper;
import com.edu.pojo.Test;
import com.edu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> findAll() {
        return testMapper.findAll();
    }
}
