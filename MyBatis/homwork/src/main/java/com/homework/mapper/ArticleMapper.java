package com.homework.mapper;

import com.homework.pojo.Article;

import java.util.List;

public interface ArticleMapper {
    List<Article> findAllWithComment();
}
