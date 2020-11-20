package com.prim.reflection;

public class Goods {
    private String id;
    private String name;
    private String desp;
    private Float price;

    public Goods() {
    }

    public Goods(String id, String name, String desp, Float price) {
        this.id = id;
        this.name = name;
        this.desp = desp;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void display(){
        System.out.println("Goods{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desp='" + desp + '\'' +
                ", price=" + price +
                '}');
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desp='" + desp + '\'' +
                ", price=" + price +
                '}';
    }
}
