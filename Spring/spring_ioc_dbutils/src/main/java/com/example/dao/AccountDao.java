package com.example.dao;

import com.example.pojo.Account;

import java.util.List;

public interface AccountDao {
    List<Account> findAll();

    Account findById(Integer id);

    void save(Account account);

    void update(Account account);

    void delete(Integer id);
}
