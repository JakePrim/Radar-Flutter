package com.prim.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Goods;

import java.util.List;

/**
 * @program: homework
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:15
 * @PackageName: com.prim.goods.service
 * @ClassName: GoodsService.java
 **/
public interface GoodsService {
    /**
     * 根据商品分类查询
     *
     * @param page
     * @param type
     * @return
     */
    IPage<Goods> selectGoodsPageType(Page<Goods> page, Integer type);

    //库存范围
    IPage<Goods> selectGoodsPageStock(Page<Goods> page, String startStock, String endStock);

    //上架状态
    IPage<Goods> selectGoodsPageFlag(Page<Goods> page, String flag);


    //价格范围
    IPage<Goods> selectGoodsPagePrice(Page<Goods> page, String startPrice, String endPrice);

    //商品名称
    IPage<Goods> selectGoodsPageName(Page<Goods> page, String name);

    /**
     * 删除商品信息
     *
     * @param id
     */
    int deleteGoodsById(Integer id);

    /**
     * 更新商品信息
     *
     * @param products
     */
    int updateGoods(Goods products);

    /**
     * 根据id查询商品
     *
     * @param id
     * @return
     */
    Goods findById(Integer id);

    /**
     * 根据一组id查询商品列表
     *
     * @param id
     * @return
     */
    List<Goods> findByInId(List<Integer> id);

    /**
     * 根据订单id获取商品信息
     *
     * @param orderId
     * @return
     */
    List<Goods> findByOrderId(Integer orderId);
}
