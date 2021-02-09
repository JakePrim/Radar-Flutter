package com.sfl.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * 课程留言点赞表(CourseCommentFavoriteRecord)实体类
 *
 * @author sfl
 * @since 2021-02-07 15:57:08
 */
public class CourseCommentFavoriteRecord implements Serializable {
    private static final long serialVersionUID = -75100621958995731L;
    /**
     * 用户评论点赞j记录ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户评论ID
     */
    private Integer commentId;
    /**
     * 是否删除，0：未删除（已赞），1：已删除（取消赞状态）
     */
    private Object isDel;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Object getIsDel() {
        return isDel;
    }

    public void setIsDel(Object isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
