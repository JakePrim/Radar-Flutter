package com.prim.common.pojo.bo;

import lombok.Data;

/**
 * @program: homework
 * @Description: 前端传递的参数封装
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:50
 * @PackageName: com.prim.common.pojo.bo
 * @ClassName: ProductPageBo.java
 **/
@Data
public class ProductPageBo {
    private Integer id;
    private Integer page;
    private Integer pageSize;
}
