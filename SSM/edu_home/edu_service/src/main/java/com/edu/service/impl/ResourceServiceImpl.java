package com.edu.service.impl;

import com.edu.dao.ResourceMapper;
import com.edu.pojo.Resource;
import com.edu.pojo.vo.ResourceVO;
import com.edu.service.ResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {


    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public PageInfo<Resource> findAllResourceByPage(ResourceVO resourceVO) {
        //开启分页
        PageHelper.startPage(resourceVO.getCurrentPage(), resourceVO.getPageSize());
        List<Resource> resources = resourceMapper.findAllResourceByPage(resourceVO);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);
        return pageInfo;
    }

    @Override
    public void saveResource(Resource resource) {
        Date date = new Date();
        resource.setCreatedTime(date);
        resource.setUpdatedTime(date);
        resource.setCreatedBy("system");
        resource.setUpdatedBy("system");

        resourceMapper.saveResource(resource);
    }

    @Override
    public void updateResource(Resource resource) {
        Date date = new Date();
        resource.setUpdatedTime(date);
        resource.setUpdatedBy("system");

        resourceMapper.updateResource(resource);
    }

    @Transactional
    @Override
    public void deleteResource(Integer id) {
        //删除 资源数据
        resourceMapper.deleteResource(id);
        //删除资源和角色的关联
        resourceMapper.deleteResourceContextRole(id);
    }
}
