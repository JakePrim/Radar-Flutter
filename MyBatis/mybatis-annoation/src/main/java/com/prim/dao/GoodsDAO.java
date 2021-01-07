package com.prim.dao;

import com.prim.dto.GoodsDTO;
import com.prim.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author prim
 */
public interface GoodsDAO {
    /**
     * 按价格查询
     * @param min
     * @param max
     * @param count
     * @return {@link Goods} 列表
     */
    @Select("select * from t_goods where current_price between #{min} and #{max} limit 0,#{count}")
    List<Goods> selectPriceRange(@Param("min") int min, @Param("max") int max, @Param("count") int count);

    /**
     * 插入商品信息
     * @param goods
     * @return
     */
    @Insert("insert into t_goods(title,sub_title,original_cost,current_price,discount,is_free_delivery,category_id)" +
            " values (#{title},#{subTitle},#{originalCost},#{currentPrice},#{discount},#{isFreeDelivery},#{categoryId})")
    @SelectKey(statement = "select last_insert_id()",before = false,resultType = Integer.class,keyProperty = "goodsId")
    int insert(Goods goods);

    /**
     * 返回结果映射集合的注解处理
     * @return
     */
    @Select("select g.*,c.* from t_goods g,t_category c where g.category_id = c.category_id")
    @Results({
         @Result(property = "goods.goodsId",column = "goods_id",id = true),
         @Result(property = "goods.title",column = "title"),
         @Result(property = "goods.subTitle",column = "sub_title"),
         @Result(property = "goods.originalCost",column = "original_cost"),
         @Result(property = "goods.currentPrice",column = "current_price"),
         @Result(property = "goods.discount",column = "discount"),
         @Result(property = "goods.isFreeDelivery",column = "is_free_delivery"),
         @Result(property = "goods.categoryId",column = "category_id"),
         @Result(property = "category.categoryName",column = "category_name"),
         @Result(property = "category.parentId",column = "parent_id"),
         @Result(property = "category.categoryLevel",column = "category_level"),
         @Result(property = "category.categoryOrder",column = "category_order")
    })
    List<GoodsDTO> selectDTO();
}
