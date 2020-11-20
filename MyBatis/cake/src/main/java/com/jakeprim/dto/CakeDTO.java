package com.jakeprim.dto;

import com.jakeprim.pojo.Cake;
import com.jakeprim.pojo.Category;

/**
 * @author prim
 */
public class CakeDTO {
    private Cake cake;
    private Category category;

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CakeDTO{" +
                "cake=" + cake +
                ", category=" + category +
                '}';
    }
}
