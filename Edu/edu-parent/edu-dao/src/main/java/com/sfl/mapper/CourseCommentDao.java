package com.sfl.mapper;

import com.sfl.pojo.CourseComment;
import com.sfl.pojo.CourseCommentFavoriteRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程留言表(CourseComment)表数据库访问层
 *
 * @author sfl
 * @since 2021-02-07 15:01:52
 */
public interface CourseCommentDao {
    Integer saveComment(CourseComment courseComment);

    List<CourseComment> findCommentByCourseId(@Param("courseId") Integer courseId);

    Integer existsFavoriteByUser(@Param("commentId") Integer commentId, @Param("userId") Integer userId);

    Integer saveFavorite(CourseCommentFavoriteRecord courseCommentFavoriteRecord);

    Integer updateFavorite(@Param("isDel") Integer isDel, @Param("commentId") Integer commentId, @Param("userId") Integer userId);

    Integer updateAddLikeCount(Integer id);

    Integer updateSubLikeCount(Integer id);

    Integer findIsDel(@Param("commentId") Integer commentId, @Param("userId") Integer userId);
}

