package com.prim.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prim.pojo.User;

import java.util.List;

/**
 * @program: MyBatis-PlusDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 11:30
 * @PackageName: com.prim.mapper
 * @ClassName: UserMapper.java
 **/
public interface UserMapper extends BaseMapper<User> {
    List<User> findAll();
}
