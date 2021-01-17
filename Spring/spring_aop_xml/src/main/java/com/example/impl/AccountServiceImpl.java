package com.example.impl;

import com.example.AccountService;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    /**
     * 目标方法-切入点
     */
    @Override
    public void transfer() {
        System.out.println("转账方法执行了");
    }
}
