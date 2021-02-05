package com.sfl.service;

import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @program: edu-web
 * @Description: 用户服务接口 : 注意接口的包名和类名必须和服务提供的接口一致
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 14:36
 * @PackageName: com.sfl.service
 * @ClassName: UserService.java
 **/
public interface UserService {
    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    ResultDTO<User> login(String phone, String password);

    /**
     * 检查手机号是否存在
     *
     * @param phone
     * @return
     */
    Integer checkPhone(String phone);

    /**
     * 注册用户
     *
     * @param phone
     * @param password
     * @return
     */
    Integer register(String phone, String password);
}
