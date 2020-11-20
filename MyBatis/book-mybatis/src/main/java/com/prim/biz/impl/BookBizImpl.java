package com.prim.biz.impl;

import com.prim.biz.BookBiz;
import com.prim.dao.BookDao;
import com.prim.global.DaoFactory;
import com.prim.pojo.Book;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author prim
 */
public class BookBizImpl implements BookBiz {
    @Override
    public List<Book> selectBookByCid(int categoryId) {
        BookDao dao = DaoFactory.getInstance().getDao(BookDao.class, true);
        return dao.selectBookByCid(categoryId);
    }

    @Override
    public int batchBook(List<Book> books) {
        SqlSession sqlSession = null;
        int num = 0;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            BookDao bookDao = sqlSession.getMapper(BookDao.class);
            num = bookDao.batchBook(books);
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
    public List<Book> selectAll() {
        BookDao bookDao = DaoFactory.getInstance().getDao(BookDao.class, true);
        return bookDao.selectAll();
    }
}
