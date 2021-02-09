package com.sfl.pojo.bo;

/**
 * @program: Edu
 * @Description: 分页参数
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 15:40
 * @PackageName: com.sfl.pojo.bo
 * @ClassName: PageBo.java
 **/
public class PageBo {
    private Integer courseId;
    private Integer page;
    private Integer pageSize;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
