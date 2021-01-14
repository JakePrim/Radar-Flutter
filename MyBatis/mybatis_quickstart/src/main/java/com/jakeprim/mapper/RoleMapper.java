package com.jakeprim.mapper;

import com.jakeprim.domain.Role;

import java.util.List;

public interface RoleMapper {
    //根据用户id查询对应角色
    List<Role> findByUid(Integer uid);
}
