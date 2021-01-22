package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Resource;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.vo.ResourceVO;
import com.edu.service.ResourceService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/findAllResource")
    public ResponseResult findAllResource(@RequestBody ResourceVO resourceVO) {
        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVO);

        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), pageInfo);
        return responseResult;
    }
}
