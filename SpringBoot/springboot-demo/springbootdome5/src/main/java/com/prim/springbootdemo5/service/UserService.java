package com.prim.springbootdemo5.service;

import com.prim.springbootdemo5.pojo.TUser;

import java.util.List;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-02 07:51
 * @PackageName: com.prim.springbootdemo5.service
 * @ClassName: UserService.java
 **/
public interface UserService {
    List<TUser> queryAll();

    int delete(Integer id);

    int insert(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int update(TUser record);
}
