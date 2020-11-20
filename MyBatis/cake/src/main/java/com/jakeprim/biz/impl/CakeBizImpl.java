package com.jakeprim.biz.impl;

import com.jakeprim.biz.CakeBiz;
import com.jakeprim.dao.CakeDao;
import com.jakeprim.dto.CakeDTO;
import com.jakeprim.global.DaoFactory;
import com.jakeprim.pojo.Cake;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author prim
 */
public class CakeBizImpl implements CakeBiz {
    public int insert(Cake cake) {
        SqlSession sqlSession = null;
        int num = 0;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            num = cakeDao.insert(cake);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return num;
    }

    public int delete(int id) {
        SqlSession sqlSession = null;
        int num = 0;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            num = cakeDao.delete(id);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return num;
    }

    public int update(Cake cake) {
        SqlSession sqlSession = null;
        int num = 0;
        try {
            sqlSession = DaoFactory.getInstance().openSession(false);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            num = cakeDao.update(cake);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sqlSession != null) {
                sqlSession.rollback();
            }
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return num;
    }

    public CakeDTO selectById(int id) {
        SqlSession sqlSession = null;
        CakeDTO cakeDTO = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            cakeDTO = cakeDao.selectById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return cakeDTO;
    }

    public List<CakeDTO> selectAll() {
        SqlSession sqlSession = null;
        List<CakeDTO> cakeDTOList = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            cakeDTOList = cakeDao.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return cakeDTOList;
    }

    public List<CakeDTO> selectByCategory(int cid) {
        SqlSession sqlSession = null;
        List<CakeDTO> cakeDTOList = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            cakeDTOList = cakeDao.selectByCategory(cid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return cakeDTOList;
    }

    public List<CakeDTO> selectByStatus(String status) {
        SqlSession sqlSession = null;
        List<CakeDTO> cakeDTOList = null;
        try {
            sqlSession = DaoFactory.getInstance().openSession(true);
            CakeDao cakeDao = sqlSession.getMapper(CakeDao.class);
            cakeDTOList = cakeDao.selectByStatus(status);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DaoFactory.closeSession(sqlSession);
        }
        return cakeDTOList;
    }
}
