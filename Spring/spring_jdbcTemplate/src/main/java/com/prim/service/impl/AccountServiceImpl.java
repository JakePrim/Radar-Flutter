package com.prim.service.impl;

import com.prim.dao.AccountDao;
import com.prim.domain.Account;
import com.prim.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("accountService")
@Transactional //注意在类上添加：@Transactional注解 会对该类的所有方法进行事务控制
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Integer id) {
        accountDao.delete(id);
    }

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, timeout = -1, readOnly = false)
    @Override
    public void transfer(String outUser, String inUser, Double money) {
        accountDao.out(outUser, money);
        int i = 1 / 0;
        accountDao.in(inUser, money);
    }
}
