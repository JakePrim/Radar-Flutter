package com.prim.dao;

import com.prim.domain.Account;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface AccountDao {
    /**
     * 查询所有账户
     *
     * @return
     */
    List<Account> findAll();

    /**
     * 根据id查询账户
     */
    Account findById(Integer id);

    /**
     * 添加账户
     *
     * @param account
     */
    void save(Account account);

    /**
     * 更新账户
     *
     * @param account
     */
    void update(Account account);

    /**
     * 删除账户
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 转出操作
     * @param outUser
     * @param money
     */
    void out(String outUser,Double money);

    /**
     * 转入操作
     * @param inUser
     * @param money
     */
    void in(String inUser,Double money);
}
