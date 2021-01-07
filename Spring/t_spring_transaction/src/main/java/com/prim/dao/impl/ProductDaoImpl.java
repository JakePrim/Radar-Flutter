package com.prim.dao.impl;

import com.prim.dao.ProductDao;
import com.prim.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(Product order) {
        jdbcTemplate.update("insert into product values(?,?,?,?,?)", order.getId(),
                order.getTitle(), order.getPrice(), order.getStock(), order.getStatus());
    }

    public void update(Product order) {
        jdbcTemplate.update("update product set title=?,price=?,stock=?,status=? where id=?",
                order.getTitle(), order.getPrice(), order.getStock(), order.getStatus(), order.getId());
    }

    public void delete(String id) {
        jdbcTemplate.update("delete from product where id=?", id);
    }

    public Product findOne(String id) {
        return jdbcTemplate.queryForObject("select * from product where id=?", new ProduceRowMapper(), id);
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", new ProduceRowMapper());
    }

    private static class ProduceRowMapper implements RowMapper<Product> {

        public Product mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Product(resultSet.getString("id"), resultSet.getString("title"),
                    resultSet.getDouble("price"), resultSet.getInt("stock"),
                    resultSet.getString("status"));
        }
    }
}
