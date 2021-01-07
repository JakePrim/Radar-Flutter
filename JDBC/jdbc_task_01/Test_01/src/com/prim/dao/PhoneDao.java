package com.prim.dao;

import com.prim.entity.Phone;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class PhoneDao {
    //需求1:  查询价格高于2000元，生产日期是2019年之前的所有手机
    public List<Phone> findByPhonesPrice(double price, String date) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from phone where price > ? and prodate < ?";
        List<Phone> phones = qr.query(sql, new BeanListHandler<>(Phone.class), price, date);
        return phones;
    }

    // 需求2:  查询所有颜色是白色的手机信息
    public List<Phone> findByPhonesColor(String color) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from phone where color=?";
        List<Phone> phones = qr.query(sql, new BeanListHandler<>(Phone.class), color);
        return phones;
    }

}
