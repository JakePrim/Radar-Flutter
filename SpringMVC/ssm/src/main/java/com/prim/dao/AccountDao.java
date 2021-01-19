package com.prim.dao;

import com.prim.domain.Account;

import java.util.List;

public interface AccountDao {
    /**
     * 查询所有账户
     */
    List<Account> findAll();

    void save(Account account);

    Account findById(Integer id);

    void update(Account account);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
