package com.edu.dao;

import com.edu.pojo.Resource;
import com.edu.pojo.ResourceCategory;
import com.edu.pojo.vo.ResourceVO;

import java.util.List;

public interface ResourceMapper {

    /**
     * 资源分页以及多条件查询
     */
    List<Resource> findAllResourceByPage(ResourceVO resourceVO);

    /**
     * 添加资源
     *
     * @param resource
     */
    void saveResource(Resource resource);

    /**
     * 更新资源
     *
     * @param resource
     */
    void updateResource(Resource resource);

    /**
     * 删除资源
     */
    void deleteResource(Integer id);

    /**
     * 在删除资源的同时删除和角色的关联关系
     *
     * @param resourceId
     */
    void deleteResourceContextRole(Integer resourceId);

    /**
     * 回显资源的资源分类信息
     *
     * @return
     */
    ResourceCategory findResourceCategoryById(Integer id);
}
