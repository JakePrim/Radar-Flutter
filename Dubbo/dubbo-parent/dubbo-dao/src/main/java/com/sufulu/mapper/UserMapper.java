package com.sufulu.mapper;

import com.sufulu.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserMapper {
    int register(User user);

    int delete(Integer id);

    int update(User user);

    List<User> findByName(String username);

    User findById(Integer id);
}
