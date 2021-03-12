package com.prim.common.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (Products)实体类
 *
 * @author sfl
 * @since 2021-03-03 08:03:57
 */
@Data
@Table(name = "products")
public class Goods implements Serializable {
    private static final long serialVersionUID = -91945519164421764L;

    @Id
    private Integer id;

    private String name;

    //价格
    private Object price;

    //上架状态
    private String flag;

    private String goodsDesc;

    private String images;

    //库存
    private Integer goodsStock;

    //商品分类
    private String goodsType;
}
