package com.homework.homeword01.dao.impl;

import com.homework.homeword01.dao.AdminDao;
import com.homework.homeword01.pojo.Admin;
import com.homework.homeword01.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {
    @Override
    public Admin login(Admin admin) {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from t_user where username=? and password=?";
        Admin ad = null;
        try {
            ad = qr.query(sql, new BeanHandler<>(Admin.class), admin.getUsername(), admin.getPassword());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ad;
    }
}
