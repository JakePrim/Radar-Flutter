package com.prim.service;

import com.prim.pojo.Staff;

public interface SelfService {
    Staff login(String account, String password);

    void changePassword(Integer id, String password);
}
