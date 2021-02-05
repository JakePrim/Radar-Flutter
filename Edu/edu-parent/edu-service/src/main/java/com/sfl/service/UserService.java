package com.sfl.service;

import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2021-02-04 15:39:46
 */
public interface UserService {
    ResultDTO<User> login(String phone, String password);

    Integer checkPhone(String phone);

    Integer register(String phone, String password);
}
