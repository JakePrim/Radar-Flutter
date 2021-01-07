package com.prim.biz.impl;

import com.prim.biz.CategoryBiz;
import com.prim.dao.CategoryDao;
import com.prim.global.DaoFactory;
import com.prim.pojo.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CategoryBizImpl implements CategoryBiz {
    @Override
    public List<Category> selectAll() {
        CategoryDao categoryDao = DaoFactory.getInstance().getDao(CategoryDao.class, true);
        return categoryDao.selectAll();
    }

    @Override
    public Category selectById(int id) {
        CategoryDao categoryDao = DaoFactory.getInstance().getDao(CategoryDao.class, true);
        return categoryDao.selectById(id);
    }

    @Override
    public int addCategory(Category category) {
        SqlSession sqlSession = null;
        int num = 0;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CategoryDao categoryDao = sqlSession.getMapper(CategoryDao.class);
            num = categoryDao.addCategory(category);
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

    @Override
    public int delete(int id) {
        SqlSession sqlSession = null;
        int num = 0;
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
}
