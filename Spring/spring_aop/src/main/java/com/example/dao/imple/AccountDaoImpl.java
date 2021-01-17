package com.example.dao.imple;

import com.example.dao.AccountDao;
import com.example.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("accountDao") //生成该类实例 存到IOC容器中
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private QueryRunner queryRunner;

    //获取连接 使转出操作和转入操作的连接是同一个连接
    @Autowired
    private ConnectionUtils connectionUtils;

    /**
     * 转出操作
     *
     * @param outUser
     * @param money
     */
    @Override
    public void outUser(String outUser, Double money) {
        String sql = "update account set money = money - ? where name = ?";
        try {
            queryRunner.update(connectionUtils.getThreadConnection(), sql, money, outUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 转入操作
     *
     * @param inUser
     * @param money
     */
    @Override
    public void inUser(String inUser, Double money) {
        String sql = "update account set money = money + ? where name = ?";
        try {
            queryRunner.update(connectionUtils.getThreadConnection(), sql, money, inUser);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
