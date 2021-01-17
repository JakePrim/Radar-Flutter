package com.prim.dao.impl;

import com.prim.dao.AccountDao;
import com.prim.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

    @Autowired //获取jdbcTemplate实例
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Account> findAll() {
        String sql = "select * from account";
        List<Account> accounts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
        return accounts;
    }

    @Override
    public Account findById(Integer id) {
        String sql = "select * from account where id = ?";
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), id);
        return account;
    }

    @Override
    public void save(Account account) {
        String sql = "insert into account(name,money) values(?,?)";
        int update = jdbcTemplate.update(sql, account.getName(), account.getMoney());
    }

    @Override
    public void update(Account account) {
        String sql = "update account set money=? where name = ?";
        int update = jdbcTemplate.update(sql, account.getMoney(), account.getName());
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from account where id = ?";
        int update = jdbcTemplate.update(sql, id);
    }

    @Override
    public void out(String outUser, Double money) {
        String sql = "update account set money=money-? where name = ?";
        int update = jdbcTemplate.update(sql, money, outUser);
    }

    @Override
    public void in(String inUser, Double money) {
        String sql = "update account set money=money+? where name = ?";
        int update = jdbcTemplate.update(sql, money, inUser);
    }
}
