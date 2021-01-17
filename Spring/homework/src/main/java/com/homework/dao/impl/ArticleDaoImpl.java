package com.homework.dao.impl;

import com.homework.dao.ArticleDao;
import com.homework.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * DAO层
 */
@Repository //添加到IOC容器中
public class ArticleDaoImpl implements ArticleDao {

    @Autowired //注入jdbcTemplate对象
    private JdbcTemplate jdbcTemplate;

    /**
     * 保存文章信息
     *
     * @param article
     */
    @Override
    public void save(Article article) {
        String sql = "insert into t_article(title, content) VALUES (?,?)";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent());
    }
}
