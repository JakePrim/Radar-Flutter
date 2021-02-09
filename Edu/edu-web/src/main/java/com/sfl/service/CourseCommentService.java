package com.sfl.service;

import com.github.pagehelper.PageInfo;
import com.sfl.pojo.CourseComment;
import com.sfl.pojo.CourseCommentFavoriteRecord;
import com.sfl.pojo.bo.PageBo;

/**
 * @program: edu-web
 * @Description: 获取远程留言服务
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 15:18
 * @PackageName: com.sfl.service
 * @ClassName: CourseCommentService.java
 **/
public interface CourseCommentService {
    Integer saveComment(CourseComment courseComment);

    PageInfo<CourseComment> findCommentByCourseId(PageBo pageBo);

    Integer favoriteComment(CourseCommentFavoriteRecord record);
}
