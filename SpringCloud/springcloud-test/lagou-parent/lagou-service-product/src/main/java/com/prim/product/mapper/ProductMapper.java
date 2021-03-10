package com.prim.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.mapper.Mapper;
import com.prim.common.pojo.Products;

/**
 * @program: springclouddemo
 * @Description: 使用Mybatis-plus该组件是mybatis的加强版 能够与springboot进行非常友好的整合，对比mybatsi框架只有使用便捷的改变没有具体功能的改变
 * 具体使用只需要让mapper接口继承BaseMapper即可
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 21:38
 * @PackageName: com.prim.product.mapper
 * @ClassName: ProductMapper.java
 **/
public interface ProductMapper extends BaseMapper<Products> {
    public void deleteByProductName();
}
