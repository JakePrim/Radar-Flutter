package com.sfl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2021-02-04 15:39:38
 */
@Data //get和set就全部自动生成了
@AllArgsConstructor //全参数构造方法
@NoArgsConstructor //无参数构造方法
@ToString //生成toString方法
public class User implements Serializable {
    private static final long serialVersionUID = -18148714368521317L;
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 用户头像地址
     */
    private String portrait;
    /**
     * 注册手机
     */
    private String phone;
    /**
     * 用户密码（可以为空，支持只用验证码注册、登录）
     */
    private String password;
    /**
     * 注册ip
     */
    private String regIp;
    /**
     * 是否有效用户
     */
    private Object accountNonExpired;
    /**
     * 账号是否未过期
     */
    private Object credentialsNonExpired;
    /**
     * 是否未锁定
     */
    private Object accountNonLocked;
    /**
     * 用户状态：ENABLE能登录，DISABLE不能登录
     */
    private String status;
    /**
     * 是否删除
     */
    private Object isDel;
    /**
     * 注册时间
     */
    private Date createTime;
    /**
     * 记录更新时间
     */
    private Date updateTime;
}
