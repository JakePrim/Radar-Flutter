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
public class Products implements Serializable {
    private static final long serialVersionUID = -91945519164421764L;

    @Id
    private Integer id;

    private String name;

    private Object price;

    private String flag;

    private String goodsDesc;

    private String images;

    private Integer goodsStock;

    private String goodsType;
}
