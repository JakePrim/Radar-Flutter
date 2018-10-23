package com.linksu.videofeed.demo.test;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2018/10/23 - 2:08 PM
 */
public class TestVideoBean implements Serializable {
    private int id;

    private int count;

    private String url;

    private String title;

    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
