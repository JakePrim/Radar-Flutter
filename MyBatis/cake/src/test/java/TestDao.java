import com.jakeprim.dao.CategoryDao;
import com.jakeprim.dto.CategoryDTO;
import com.jakeprim.global.DaoFactory;
import com.jakeprim.pojo.Category;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.lang.annotation.Target;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TestDao {
    @Test
    public void testConnectionDatabase() {
        SqlSession sqlSession = DaoFactory.getInstance().openSession();
        Connection connection = sqlSession.getConnection();
        System.out.println("connection:" + connection);
    }

    @Test
    public void testDeleteCategory() {
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            int delete = categoryDao.delete(10005);
            System.out.println("删除成功的个数：" + delete);
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
    }

    @Test
    public void testBatchCategoryInsert() {
        SqlSession sqlSession = DaoFactory.getInstance().openSession(false);
        try {
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            /**
             * insert into catalog(id,title,pid) values(11000,'婚礼',10000);
             * insert into catalog(id,title,pid) values(11100,'西式',11000);
             * insert into catalog(id,title,pid) values(11101,'多层',11100);
             * insert into catalog(id,title,pid) values(11102,'花朵',11100);
             * insert into catalog(id,title,pid) values(11103,'造型',11100);
             */
            List<Category> categories = new ArrayList<Category>();
            Category category = new Category();
            category.setTitle("婚礼");
            category.setPid(10000);
            category.setInfo("测试插入");
            Category category2 = new Category();
            category2.setTitle("婚礼2");
            category2.setPid(10000);
            category2.setInfo("测试插入2");
            categories.add(category);
            categories.add(category2);
            int i = categoryDao.batchInsert(categories);
            System.out.println("插入成功的个数" + i);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
    }

    @Test(timeout = 200L)
    public void testGetAllCategory() {
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            Category categoryDTO = categoryDao.selectCategory(10000);//获取所有的分类
            System.out.println(categoryDTO.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
    }
}
