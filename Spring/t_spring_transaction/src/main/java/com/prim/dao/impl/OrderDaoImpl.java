package com.prim.dao.impl;

import com.prim.dao.OrderDao;
import com.prim.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("orderDao")
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Order order) {
        jdbcTemplate.update("insert into `order` values(?,?,?,?,?,?,?,?,?,?,?)",
                order.getId(), order.getPid(), order.getNumber(), order.getPrice(), order.getCreateTime()
                , order.getSendTime(), order.getConfirmTime(), order.getConsignee(),
                order.getPhone(), order.getAddress(), order.getStatus());
    }

    public void update(Order order) {
        jdbcTemplate.update("update `order` set pid=?,number=?,price=?,create_time=?,send_time=?,confirm_time=?,consignee=?,phone=?,address=?,status=? " +
                        " where id=?", order.getPid(), order.getNumber(), order.getPrice(), order.getCreateTime(), order.getSendTime(), order.getConfirmTime(),
                order.getConsignee(), order.getPhone(), order.getStatus(), order.getId());
    }

    public void delete(String id) {
        jdbcTemplate.update("delete from `order` where id=?", id);
    }

    public Order findOne(String id) {
        return jdbcTemplate.queryForObject("select * from `order` where id=?", new OrderRowMapper(), id);
    }

    public List<Order> findAll() {
        return jdbcTemplate.query("select * from `order`", new OrderRowMapper());
    }

    public static class OrderRowMapper implements RowMapper<Order> {

        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order(resultSet.getString("id"), resultSet.getString("pid"), resultSet.getInt("number"),
                    resultSet.getDouble("price"), resultSet.getString("consignee"), resultSet.getString("phone"),
                    resultSet.getString("address"));
            return order;
        }
    }
}
