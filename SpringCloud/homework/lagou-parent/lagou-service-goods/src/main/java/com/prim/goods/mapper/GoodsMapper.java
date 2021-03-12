package com.prim.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.prim.common.pojo.Goods;

import java.util.List;

/**
 * @program: homework
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 14:13
 * @PackageName: com.prim.goods.mapper
 * @ClassName: GoodsMapper.java
 **/
public interface GoodsMapper extends BaseMapper<Goods> {
    List<Goods> findByOrderId(Integer orderId);
}
