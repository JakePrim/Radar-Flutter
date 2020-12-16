package com.prim.app;

import com.prim.entity.Account;
import com.prim.utils.DateUtils;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        // 实现卡号：1122334455向55443332211转账5000元的操作
        Connection connection = DruidUtils.getConnection();
        String card1 = "1122334455";
        String card2 = "55443332211";
        QueryRunner qr = new QueryRunner();
        try {
            //由于涉及到金额转账，不能自动提交事务，需要手动提交事务
            connection.setAutoCommit(false);

            //1. 判断转出方是否有足够余额，如果不足，提示信息“金额不足“，并结束程序
            String sql = "select * from account where card=?";
            Account account = qr.query(connection, sql, new BeanHandler<>(Account.class), card1);
            if (account.getBalance() < 5000) {
                System.out.println("金额不足");
            } else {
                //2. 进行转账操作
                //1122334455 卡 金额减少5000
                sql = "update account set balance=balance-5000 where card=1122334455";
                int update = qr.update(connection, sql);
                if (update > 0) {
                    //55443332211 卡 金额增加5000
                    sql = "update account set balance=balance+5000 where card=55443332211";
                    update = qr.update(connection, sql);
                    if (update > 0) {
                        System.out.println("转账成功");
                        List<Account> accounts = qr.query(connection, "select * from account", new BeanListHandler<>(Account.class));
                        for (Account a : accounts) {
                            System.out.println(a);
                        }
                    }
                    //Transaction 记录1122334455 状态
                    String tsql = "insert into transaction values(null,?,?,?,?)";
                    Object[] params = {card1, "转出", 5000.0, DateUtils.getDateFormart()};
                    qr.update(connection, tsql, params);
                    //Transaction 记录55443332211 状态
                    Object[] params2 = {card2, "转入", 5000.0, DateUtils.getDateFormart()};
                    qr.update(connection, tsql, params2);
                } else {
                    System.out.println("转账失败");
                }
            }
            //提交事务，并且安静 的关闭连接
            DbUtils.commitAndCloseQuietly(connection);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            System.out.println("转账失败");
            try {
                List<Account> accounts = qr.query(connection, "select * from account", new BeanListHandler<>(Account.class));
                for (Account a : accounts) {
                    System.out.println(a);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //出现异常 回滚事务
            DbUtils.rollbackAndCloseQuietly(connection);
        }

    }
}
