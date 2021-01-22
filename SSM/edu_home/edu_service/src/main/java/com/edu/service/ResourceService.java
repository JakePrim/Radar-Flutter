package com.edu.service;

import com.edu.pojo.Resource;
import com.edu.pojo.vo.ResourceVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ResourceService {
    PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO);
}
