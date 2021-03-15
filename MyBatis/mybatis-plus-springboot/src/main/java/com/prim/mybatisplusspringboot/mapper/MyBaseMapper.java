package com.prim.mybatisplusspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prim.mybatisplusspringboot.pojo.User;

import java.util.List;

/**
 * @program: MyBatis-PlusDemo
 * @Description: 这是一个通用的mapper接口，以后创建其他mapper接口时不再继承basemapper而是继承mybasemapper
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 21:46
 * @PackageName: com.prim.mybatisplusspringboot.mapper
 * @ClassName: MyBaseMapper.java
 **/
public interface MyBaseMapper<T> extends BaseMapper<T> {
    List<T> findAll();
}
