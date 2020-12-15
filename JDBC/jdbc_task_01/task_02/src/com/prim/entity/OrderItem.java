package com.prim.entity;

/**
 * 订单项中间表 记录多对多订单表和商品表的信息
 */
public class OrderItem {
    private String itemid;
    private String pid;
    private String oid;

    private Product product;//保存订单项中商品的详细信息

    private Orders orders;//保存订单项中订单的详细信

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid='" + itemid + '\'' +
                ", pid='" + pid + '\'' +
                ", oid='" + oid + '\'' +
                ", product=" + product +
                ", orders=" + orders +
                '}';
    }
}
