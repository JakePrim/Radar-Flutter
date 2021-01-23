package com.edu.service;

import com.edu.pojo.ResourceCategory;

import java.util.List;

public interface ResourceCategoryService {
    List<ResourceCategory> findAllResourceCategory();

    /**
     * 新增资源分类
     */
    void saveResourceCategory(ResourceCategory resourceCategory);

    /**
     * 更新资源分类
     *
     * @param resourceCategory
     */
    void updateResourceCategory(ResourceCategory resourceCategory);

    /**
     * 删除资源分类
     * @param id
     */
    void deleteResourceCategory(Integer id);
}
