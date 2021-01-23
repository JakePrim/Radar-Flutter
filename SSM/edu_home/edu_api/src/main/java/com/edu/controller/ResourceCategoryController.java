package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.ResourceCategory;
import com.edu.pojo.ResponseResult;
import com.edu.service.ResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resourceCategory")
public class ResourceCategoryController {

    @Autowired
    private ResourceCategoryService resourceCategoryService;

    @GetMapping("/findAllResourceCategory")
    public ResponseResult findAllResourceCategory() {
        List<ResourceCategory> categoryList = resourceCategoryService.findAllResourceCategory();

        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), categoryList);
        return responseResult;
    }

    /**
     * 更新或保存资源分类信息
     *
     * @param resourceCategory
     * @return
     */
    @PostMapping("/saveOrUpdateResourceCategory")
    public ResponseResult saveOrUpdateResourceCategory(@RequestBody ResourceCategory resourceCategory) {
        if (resourceCategory.getId() != null) {
            resourceCategoryService.updateResourceCategory(resourceCategory);
        } else {
            resourceCategoryService.saveResourceCategory(resourceCategory);
        }
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return responseResult;
    }

    /**
     * 删除资源分类
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteResourceCategory")
    public ResponseResult deleteResourceCategory(Integer id) {
        resourceCategoryService.deleteResourceCategory(id);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return responseResult;
    }
}
