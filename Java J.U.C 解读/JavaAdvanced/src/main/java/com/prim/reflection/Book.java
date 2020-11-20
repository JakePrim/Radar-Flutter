package com.prim.reflection;

public class Book {
    private String id;
    private String name;
    public float price;

    public Book() {
    }

    public Book(String id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
