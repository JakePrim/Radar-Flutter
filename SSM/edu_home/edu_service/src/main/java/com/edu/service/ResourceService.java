package com.edu.service;

import com.edu.pojo.Resource;
import com.edu.pojo.vo.ResourceVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ResourceService {
    PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO);

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
}
