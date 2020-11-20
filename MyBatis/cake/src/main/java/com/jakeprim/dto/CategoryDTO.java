package com.jakeprim.dto;

import com.jakeprim.pojo.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类映射结果集合 父类和子类
 * @author prim
 */
public class CategoryDTO {
    /**
     * 父类
     */
    private Category category;

    /**
     * 子类
     */
    private List<Category> children;

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Category getParent() {
        return category;
    }

    public void setParent(Category parent) {
        this.category = parent;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "parent=" + category +
                ", children=" + children +
                '}';
    }
}
