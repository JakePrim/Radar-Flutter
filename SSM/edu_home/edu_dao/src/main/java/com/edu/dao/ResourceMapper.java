package com.edu.dao;

import com.edu.pojo.Resource;
import com.edu.pojo.vo.ResourceVO;

import java.util.List;

public interface ResourceMapper {

    /**
     * 资源分页以及多条件查询
     */
    List<Resource> findAllResourceByPage(ResourceVO resourceVO);
}
