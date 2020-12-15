package com.prim.dao;

import com.prim.entity.Category;
import com.prim.entity.Product;
import com.prim.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
    /**
     * 获取商品的信息
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Product findById(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from product where pid=?";
        //避免多表连查 尽量分开查询
        Product product = qr.query(sql, new BeanHandler<>(Product.class), id);
        //设置商品分类的详细信息
        product.setCategory(findByCategoryId(product.getCid()));
        return product;
    }

    /**
     * 通过分类id来获取分类的信息
     *
     * @param cid
     * @return
     * @throws SQLException
     */
    public Category findByCategoryId(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from category where cid=?";
        Category category = qr.query(sql, new BeanHandler<>(Category.class), cid);
        return category;
    }

    /**
     * 获取指定分类下的商品个数
     *
     * @param cid
     * @return
     */
    public int getCount(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select count(*) from product where cid=?";
        Long l = qr.query(sql, new ScalarHandler<>(), cid);
        return l.intValue();
    }

    /**
     * 获取某个分类id下的所有商品信息
     * @param cid
     * @return
     * @throws SQLException
     */
    public List<Product> findAllProductByCid(String cid) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from product where cid=?";
        List<Product> productList = qr.query(sql, new BeanListHandler<>(Product.class), cid);
        return productList;
    }
}
