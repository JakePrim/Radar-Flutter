package com.edu.dao;

import com.edu.pojo.ResourceCategory;

import java.util.List;

public interface ResourceCategoryMapper {

    /**
     * 查询所有资源分类信息
     *
     * @return
     */
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
     */
    void deleteResourceCategory(Integer id);
}
