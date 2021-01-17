package com.example.service;

public interface AccountService {
    void transfer(String outUser, String inUser, Double money);

    void save();

    void update();

    void delete();
}
