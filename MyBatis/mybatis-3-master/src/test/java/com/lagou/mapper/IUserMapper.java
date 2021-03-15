package com.lagou.mapper;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserMapper {

    //查询所有用户信息，同时查询出每个用户关联的订单信息
    public List<User>  findAll();


    public User findById(Integer id);
}
