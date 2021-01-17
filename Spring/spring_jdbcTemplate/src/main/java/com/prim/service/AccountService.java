package com.prim.service;

import com.prim.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    /**
     * 根据id查询账户
     */
    Account findById(Integer id);

    /**
     * 添加账户
     *
     * @param account
     */
    void save(Account account);

    /**
     * 更新账户
     *
     * @param account
     */
    void update(Account account);

    /**
     * 删除账户
     *
     * @param id
     */
    void delete(Integer id);


    void transfer(String outUser, String inUser, Double money);
}
