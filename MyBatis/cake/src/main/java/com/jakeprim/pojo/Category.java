package com.jakeprim.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类表实体
 *
 * @author prim
 */
public class Category {
    private Integer id;
    private String title;
    private Integer pid;
    private String info;
    private List<Category> children;

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", info='" + info + '\'' +
                '}';
    }
}
