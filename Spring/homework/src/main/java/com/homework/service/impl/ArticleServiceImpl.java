package com.homework.service.impl;

import com.homework.dao.ArticleDao;
import com.homework.pojo.Article;
import com.homework.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public void save(Article article) {
        articleDao.save(article);
        int i = 1/0; //异常情况
    }
}
