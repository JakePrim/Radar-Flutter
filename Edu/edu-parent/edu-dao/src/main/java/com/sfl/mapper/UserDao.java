package com.sfl.mapper;

import com.sfl.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2021-02-04 15:39:45
 */
public interface UserDao {
    User login(@Param("phone") String phone, @Param("password") String password);

    /**
     * 检查手机号是否注册过
     *
     * @param phone 手机号
     * @return 0：未注册；1 已注册
     */
    Integer checkPhone(@Param("phone") String phone);

    Integer register(@Param("phone") String phone, @Param("password") String password,@Param("nickname") String nickname,@Param("headimage") String headimage);

    Integer updateUserInfo(User user);

    /**
     * 修改密码
     *
     * @return
     */
    Integer updatePassword(User user);
}

