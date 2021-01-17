import com.homework.pojo.Article;
import com.homework.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class HomeWorkTest {
    @Autowired
    private ArticleService articleService;

    @Test
    public void test() {
        Article article = new Article();
        article.setTitle("新建文章1");
        article.setContent("文章内容");

        articleService.save(article);
    }
}
