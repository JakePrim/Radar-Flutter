package com.sfl.service;

import com.github.pagehelper.PageInfo;
import com.sfl.pojo.CourseComment;
import com.sfl.pojo.CourseCommentFavoriteRecord;
import com.sfl.pojo.bo.PageBo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程留言表(CourseComment)表服务接口
 *
 * @author sfl
 * @since 2021-02-07 15:01:55
 */
public interface CourseCommentService {
    Integer saveComment(CourseComment courseComment);

    PageInfo<CourseComment> findCommentByCourseId(PageBo pageBo);

    Integer favoriteComment(CourseCommentFavoriteRecord record);
}
