package com.prim.springbootdata;

import com.prim.springbootdata.mapper.ArticleDao;
import com.prim.springbootdata.mapper.CommentMapper;
import com.prim.springbootdata.pojo.Article;
import com.prim.springbootdata.pojo.Comment;
import com.prim.springbootdata.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootDataApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleDao articleDao;

    @Test
    void contextLoads() {
        Comment comment = commentMapper.findById("1");
        System.out.println(comment);

        Article article = articleDao.selectByPrimaryKey(1);
        System.out.println(article);
    }

    @Autowired
    private RedisUtils redisUtils;//IOC获取对象

    @Test
    void testRedis() {
        redisUtils.set("k1", articleDao.selectByPrimaryKey(1));
        System.out.println("success");
    }

    @Test
    void readRedis() {
        Article article = (Article) redisUtils.get("k1");
        System.out.println(article);//Article{id=1, title='Spring Boot基础入门', content='从入门到精通讲解...'}
        redisUtils.delete("k1");//
        Article article2 = (Article) redisUtils.get("k1");
        System.out.println(article2);//null
    }

}
