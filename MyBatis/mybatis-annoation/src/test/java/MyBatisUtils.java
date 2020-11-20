import com.prim.dao.GoodsDAO;
import com.prim.dto.GoodsDTO;
import com.prim.pojo.Goods;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyBatisUtils {
    @Test
    public void selectDao(){
        SqlSession sqlSession = null;
        try {
            sqlSession = com.prim.utils.MyBatisUtils.openSession();
            GoodsDAO mapper = sqlSession.getMapper(GoodsDAO.class);
            List<Goods> list = mapper.selectPriceRange(100, 500, 20);
            System.out.println(list.size());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            com.prim.utils.MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void selectDto(){
        SqlSession sqlSession = null;
        try {
            sqlSession = com.prim.utils.MyBatisUtils.openSession();
            GoodsDAO mapper = sqlSession.getMapper(GoodsDAO.class);
            List<GoodsDTO> goodsDTOS = mapper.selectDTO();
            System.out.println(goodsDTOS.size());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            com.prim.utils.MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void  insertDao(){
        SqlSession sqlSession = null;
        try {
            sqlSession = com.prim.utils.MyBatisUtils.openSession();
            GoodsDAO mapper = sqlSession.getMapper(GoodsDAO.class);
            Goods goods = new Goods();
            goods.setTitle("测试商品插入");
            goods.setSubTitle("测试副标题");
            goods.setCurrentPrice(200.f);
            goods.setIsFreeDelivery(1);
            goods.setOriginalCost(100.f);
            goods.setCategoryId(43);
            goods.setDiscount(100.f);
            mapper.insert(goods);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            com.prim.utils.MyBatisUtils.closeSession(sqlSession);
        }
    }
}
