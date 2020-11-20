package com.jakeprim.biz.impl;

import com.jakeprim.biz.CategoryBiz;
import com.jakeprim.dao.CategoryDao;
import com.jakeprim.dto.CategoryDTO;
import com.jakeprim.global.DaoFactory;
import com.jakeprim.pojo.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author prim
 */
public class CategoryBizImpl implements CategoryBiz {
    public int add(List<Category> categories) {
        int num = 0;
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            num = categoryDao.batchInsert(categories);
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return num;
    }

    public int delete(int id) {
        int num = 0;
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            num = categoryDao.delete(id);
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return num;
    }

    public Category selectAll(int id) {
        Category categoryDTO = null;
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            categoryDTO = categoryDao.selectCategory(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return categoryDTO;
    }

    public Category getRoot() {
        return selectAll(10000);
    }

    public boolean selectByTitleCount(String title) {
        boolean isExists = false;
        SqlSession sqlSession = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            int i = categoryDao.selectByTitleCount(title);
            isExists = i > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return isExists;
    }
}
