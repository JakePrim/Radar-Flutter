package com.jakeprim.dao;

import com.jakeprim.pojo.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author prim
 */
public interface AccountDao {
    /**
     * 查询登录的用户
     * @param account
     * @param password
     * @return
     */
    @Select("select * from account where account=#{account} and password=#{password}")
    Account selectByAccount(@Param("account") String account, @Param("password") String password);
}
