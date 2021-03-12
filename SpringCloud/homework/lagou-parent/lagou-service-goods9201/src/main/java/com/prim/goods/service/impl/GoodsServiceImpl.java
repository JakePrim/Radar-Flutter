package com.prim.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Goods;
import com.prim.goods.mapper.GoodsMapper;
import com.prim.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: homework
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:15
 * @PackageName: com.prim.goods.service.impl
 * @ClassName: GoodsServiceImpl.java
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public IPage<Goods> selectGoodsPageType(Page<Goods> page, Integer type) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_type", type);
        Page<Goods> productsPage = goodsMapper.selectPage(page, queryWrapper);
        return productsPage;
    }

    @Override
    public IPage<Goods> selectGoodsPageStock(Page<Goods> page, String startStock, String endStock) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("goods_stock", startStock, endStock);
        Page<Goods> productsPage = goodsMapper.selectPage(page, queryWrapper);
        return productsPage;
    }

    @Override
    public IPage<Goods> selectGoodsPageFlag(Page<Goods> page, String flag) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag", flag);
        Page<Goods> productsPage = goodsMapper.selectPage(page, queryWrapper);
        return productsPage;
    }

    @Override
    public IPage<Goods> selectGoodsPagePrice(Page<Goods> page, String startPrice, String endPrice) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("price", startPrice, endPrice);
        Page<Goods> productsPage = goodsMapper.selectPage(page, queryWrapper);
        return productsPage;
    }

    @Override
    public IPage<Goods> selectGoodsPageName(Page<Goods> page, String name) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        Page<Goods> productsPage = goodsMapper.selectPage(page, queryWrapper);
        return productsPage;
    }


    @Override
    public int deleteGoodsById(Integer id) {
        return goodsMapper.deleteById(id);
    }

    @Override
    public int updateGoods(Goods products) {
        return goodsMapper.updateById(products);
    }

    @Override
    public Goods findById(Integer id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public List<Goods> findByOrderId(Integer orderId) {
        return goodsMapper.findByOrderId(orderId);
    }

    @Override
    public List<Goods> findByInId(List<Integer> id) {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.in("id", id);
        return goodsMapper.selectList(wrapper);
    }
}
