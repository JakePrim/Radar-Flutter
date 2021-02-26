package com.sfl.mapper;

import com.sfl.pojo.CourseCommentFavoriteRecord;

import java.util.List;

/**
 * @program: edu-parent
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 07:48
 * @PackageName: com.sfl.mapper
 * @ClassName: CourseCommentFavoriteRecordDao.java
 **/
public interface CourseCommentFavoriteRecordDao {
    List<CourseCommentFavoriteRecord> findByCommentId(Integer id);
}
