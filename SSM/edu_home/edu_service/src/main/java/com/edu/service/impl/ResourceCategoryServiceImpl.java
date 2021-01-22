package com.edu.service.impl;

import com.edu.dao.ResourceCategoryMapper;
import com.edu.pojo.ResourceCategory;
import com.edu.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {

    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;

    @Override
    public List<ResourceCategory> findAllResourceCategory() {
        List<ResourceCategory> categories = resourceCategoryMapper.findAllResourceCategory();
        return categories;
    }
}
