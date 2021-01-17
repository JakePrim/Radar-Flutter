package com.example.service.impl;

import com.example.dao.AccountDao;
import com.example.service.AccountService;
import com.example.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 转账方法
     *
     * @param outUser
     * @param inUser
     * @param money
     */
    @Override
    public void transfer(String outUser, String inUser, Double money) {
        //转出操作
        accountDao.outUser(outUser, money);
//        int i = 1 / 0;
        //转入操作
        accountDao.inUser(inUser, money);
    }

    @Override
    public void save() {
        System.out.println("save");
    }

    @Override
    public void update() {
        System.out.println("update");
    }

    @Override
    public void delete() {
        System.out.println("delete");
    }
}
