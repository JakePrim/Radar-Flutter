package com.prim.service;

import com.prim.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    void save(Account account);

    Account findById(Integer id);

    void update(Account account);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);
}
