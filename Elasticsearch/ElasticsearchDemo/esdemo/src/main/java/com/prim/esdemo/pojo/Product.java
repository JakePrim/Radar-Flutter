package com.prim.esdemo.pojo;

import lombok.Data;

/**
 * @program: ElasticsearchDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-17 22:51
 * @PackageName: com.prim.esdemo.pojo
 * @ClassName: Product.java
 **/
@Data
public class Product {
    private Long id;
    private String title;//标题
    private String category;//分类
    private String brand;//品牌
    private Double price;//价格
    private String images;//图片地址
}
