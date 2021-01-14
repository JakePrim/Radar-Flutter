package com.homework.test;

import com.homework.mapper.ArticleMapper;
import com.homework.pojo.Article;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {
    public static void main(String[] args) throws IOException {
        InputStream stream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(stream);
        SqlSession session = sessionFactory.openSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        List<Article> articles = mapper.findAllWithComment();
        for (Article article : articles) {
            System.out.println(article);
            System.out.println(article.getCommentList());
        }
        session.close();
    }
}
