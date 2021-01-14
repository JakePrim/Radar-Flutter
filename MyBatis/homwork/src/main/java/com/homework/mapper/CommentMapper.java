package com.homework.mapper;

import com.homework.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    List<Comment> findByAid(Integer aid);
}
