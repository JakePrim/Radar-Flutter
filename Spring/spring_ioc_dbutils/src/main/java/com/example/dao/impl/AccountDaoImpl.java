package com.example.dao.impl;

import com.example.dao.AccountDao;
import com.example.pojo.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {
    //通过Spring 注入QueryRunner对象
    @Autowired
    private QueryRunner queryRunner;

    @Override
    public List<Account> findAll() {
        List<Account> accounts = null;
        try {
            accounts = queryRunner.query("select * from account", new BeanListHandler<>(Account.class));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        Account account = null;
        try {
            account = queryRunner.query(sql, new BeanHandler<>(Account.class), id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return account;
    }

    @Override
    public void save(Account account) {
        String sql = "insert into account(name,money) values(?,?)";
        try {
            int update = queryRunner.update(sql, account.getName(), account.getMoney());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Account account) {
        String sql = "update account set name=?,money=? where id = ?";
        try {
            int update = queryRunner.update(sql, account.getName(), account.getMoney(), account.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from account where id = ?";
        try {
            int update = queryRunner.update(sql, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
