package com.edu.pojo.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserVo {
    private Integer currentPage;
    private Integer pageSize;
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startCreateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endCreateTime;


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
