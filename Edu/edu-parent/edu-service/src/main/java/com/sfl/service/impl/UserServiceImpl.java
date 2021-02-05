package com.sfl.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sfl.mapper.UserDao;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import com.sfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2021-02-04 15:39:47
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    /**
     * 用户登录/注册接口：如果手机号没有注册则自动注册登录。
     * 注意：复杂的业务逻辑最好写在service中，不要在controller。注意涉及到多个读和写操作一起进行
     * 则必须要使用事务处理
     *
     * @param phone
     * @param password
     * @return
     */
    @Transactional
    @Override
    public ResultDTO<User> login(String phone, String password) {
        ResultDTO<User> dto = new ResultDTO<>();
        User user = null;
        //检查手机号是否注册
        Integer isCheck = userDao.checkPhone(phone);
        if (isCheck == 0) {
            //未注册进行注册
            Integer row = userDao.register(phone, password);
            if (row == 0) {
                //注册失败
                dto.setState(400);
                dto.setMessage("注册失败，请稍后再试");
            } else {
                //注册成功
                dto.setState(200);
                dto.setMessage("手机号尚未注册，系统已为您自动注册，请牢记密码");
                user = userDao.login(phone, password);
                user.setPassword("");//将密码隐去 防止密码泄露
            }
        } else {
            user = userDao.login(phone, password);
            if (user != null) {
                user.setPassword("");//将密码隐去 防止密码泄露
                dto.setState(200);
                dto.setMessage("登录成功");
            } else {
                dto.setState(300);
                dto.setMessage("登录失败，用户名或密码错误");
            }
        }
        dto.setContent(user);
        return dto;
    }

    @Override
    public Integer checkPhone(String phone) {
        Integer row = userDao.checkPhone(phone);
        return row;
    }

    @Override
    public Integer register(String phone, String password) {
        Integer row = userDao.register(phone, password);
        return row;
    }
}
