package com.prim.dto;

import com.prim.pojo.Category;
import com.prim.pojo.Goods;

/**
 * 多表查询的结果映射对象
 * @author prim
 */
public class GoodsDTO {
    private Goods goods;
    private Category category;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "goods=" + goods +
                ", category=" + category +
                '}';
    }
}
