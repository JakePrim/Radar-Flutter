package com.sfl.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 课程留言表(CourseComment)实体类
 *
 * @author sfl
 * @since 2021-02-07 15:01:48
 */
public class CourseComment implements Serializable {
    private static final long serialVersionUID = 719666326571482483L;
    /**
     * 主键
     */
    private Object id;
    /**
     * 课程id
     */
    private Integer courseId;
    /**
     * 章节id
     */
    private Integer sectionId;
    /**
     * 课时id
     */
    private Integer lessonId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 运营设置用户昵称
     */
    private String userName;
    /**
     * 父级评论id
     */
    private Integer parentId;
    /**
     * 是否置顶：0不置顶，1置顶
     */
    private Object isTop;
    /**
     * 评论
     */
    private String comment;
    /**
     * 点赞数
     */
    private Integer likeCount;
    /**
     * 是否回复留言：0普通留言，1回复留言
     */
    private Object isReply;
    /**
     * 留言类型：0用户留言，1讲师留言，2运营马甲 3讲师回复 4小编回复 5官方客服回复
     */
    private Integer type;
    /**
     * 留言状态：0待审核，1审核通过，2审核不通过，3已删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Object isDel;
    /**
     * 最后操作者id
     */
    private Integer lastOperator;
    /**
     * 是否发送了通知,1表示未发出，0表示已发出
     */
    private Object isNotify;
    /**
     * 标记归属
     */
    private Object markBelong;
    /**
     * 回复状态 0 未回复 1 已回复
     */
    private Object replied;

    private List<CourseCommentFavoriteRecord> favoriteRecords = new ArrayList<>();

    public List<CourseCommentFavoriteRecord> getFavoriteRecords() {
        return favoriteRecords;
    }

    public void setFavoriteRecords(List<CourseCommentFavoriteRecord> favoriteRecords) {
        this.favoriteRecords = favoriteRecords;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Object getIsTop() {
        return isTop;
    }

    public void setIsTop(Object isTop) {
        this.isTop = isTop;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Object getIsReply() {
        return isReply;
    }

    public void setIsReply(Object isReply) {
        this.isReply = isReply;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Object getIsDel() {
        return isDel;
    }

    public void setIsDel(Object isDel) {
        this.isDel = isDel;
    }

    public Integer getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(Integer lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Object getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(Object isNotify) {
        this.isNotify = isNotify;
    }

    public Object getMarkBelong() {
        return markBelong;
    }

    public void setMarkBelong(Object markBelong) {
        this.markBelong = markBelong;
    }

    public Object getReplied() {
        return replied;
    }

    public void setReplied(Object replied) {
        this.replied = replied;
    }

    @Override
    public String toString() {
        return "CourseComment{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", sectionId=" + sectionId +
                ", lessonId=" + lessonId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", parentId=" + parentId +
                ", isTop=" + isTop +
                ", comment='" + comment + '\'' +
                ", likeCount=" + likeCount +
                ", isReply=" + isReply +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                ", lastOperator=" + lastOperator +
                ", isNotify=" + isNotify +
                ", markBelong=" + markBelong +
                ", replied=" + replied +
                ", favoriteRecords=" + favoriteRecords +
                '}';
    }
}
