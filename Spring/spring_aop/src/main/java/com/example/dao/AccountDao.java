package com.example.dao;

public interface AccountDao {
    void outUser(String outUser, Double money);

    void inUser(String inUser, Double money);
}
