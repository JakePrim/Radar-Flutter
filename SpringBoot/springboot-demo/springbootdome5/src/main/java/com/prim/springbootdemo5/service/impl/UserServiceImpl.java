package com.prim.springbootdemo5.service.impl;

import com.prim.springbootdemo5.mapper.TUserMapper;
import com.prim.springbootdemo5.pojo.TUser;
import com.prim.springbootdemo5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot-demo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-02 07:52
 * @PackageName: com.prim.springbootdemo5.service.impl
 * @ClassName: UserServiceImpl.java
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TUserMapper tUserMapper;

    @Override
    public List<TUser> queryAll() {
        return tUserMapper.queryAll();
    }

    @Override
    public int delete(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(TUser record) {
        return tUserMapper.insertSelective(record);
    }

    @Override
    public TUser selectByPrimaryKey(Integer id) {
        return tUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(TUser record) {
        return tUserMapper.updateByPrimaryKeySelective(record);
    }
}
