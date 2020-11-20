package com.prim.jedis;

/**
 * @author prim
 */
public class Goods {
    private Integer goodsId;
    private String goodsName;
    private String desc;
    private Float price;
    public Goods(){

    }

    public Goods(Integer goodsId, String goodsName, String desc, Float price) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.desc = desc;
        this.price = price;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
