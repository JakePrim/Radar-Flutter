package com.sfl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.sfl.pojo.CourseComment;
import com.sfl.pojo.CourseCommentFavoriteRecord;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.bo.PageBo;
import com.sfl.service.CourseCommentService;
import org.springframework.web.bind.annotation.*;

/**
 * @program: edu-web
 * @Description: 留言api
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 15:19
 * @PackageName: com.sfl.controller
 * @ClassName: CommentController.java
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Reference
    private CourseCommentService courseCommentService;

    @PostMapping("/saveCourseComment")
    public ResultDTO saveCourseComment(@RequestBody CourseComment courseComment) {
        Integer row = courseCommentService.saveComment(courseComment);
        if (row == 0) {
            return ResultDTO.createError("评论失败，请稍后再试");
        } else {
            return ResultDTO.createSuccess("评论成功");
        }
    }

    @GetMapping("/findCourseComment")
    public ResultDTO findCourseComment(PageBo pageBo) {
        PageInfo<CourseComment> pageInfo = courseCommentService.findCommentByCourseId(pageBo);
        return ResultDTO.createSuccess("success", pageInfo);
    }

    @PostMapping("/favoriteComment")
    public ResultDTO favoriteComment(@RequestBody CourseCommentFavoriteRecord record) {
        Integer row = courseCommentService.favoriteComment(record);
        if (row == 0){
            return ResultDTO.createSuccess("点赞成功");
        }else {
            return ResultDTO.createError("点赞取消");
        }
    }
}
