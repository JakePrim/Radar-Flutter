import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.prim.dto.GoodsDTO;
import com.prim.pojo.Classes;
import com.prim.pojo.Goods;
import com.prim.pojo.GoodsDetail;
import com.prim.pojo.Student;
import com.prim.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.*;

public class MyBatisTest {

    @Test
    public void testSqlSessionFactory() throws IOException {
        //利用Reader加载classpath下的mybatis-config.xml文件
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //初始化SqlSessionFactory对象，解析mybatis-config.xml文件
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);
        System.out.println("SqlSessionFactory:"+sqlSessionFactory);
        SqlSession sqlSession = null;
        try {
            //创建SqlSession对象，SqlSession是jdbc的扩展类，用于与数据库交互
             sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            //测试数据库连接是否成功
            System.out.println("connection:"+connection);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (sqlSession!=null) {
                //这里需要注意：如果在mybatis-config.xml 中的dataSource 设置的type为：POOLED，调用close则会将连接收回到连接池中
                //如果type设置UNPOOLED，表示直连调用close()会调用connection.close()直接关闭连接
                sqlSession.close();
            }
        }
    }

    @Test
    public void testMyBatisUtils() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println("connection"+connection);
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectAll() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询数据传递的为mapper中设置的namespace 和SQL语句的ID 这样就可以找到mapper.xml文件中的SQL语句了
            //非常便捷
            //这里需要注意 如果数据库的字段命名例如goods_id 而实体类采用驼峰命名时 那么实体类获得的就是字段值null
            //需要在mybatis-config.xml核心配置文件 添加开启驼峰命名转换就可以解决
            List<Goods> list = sqlSession.selectList("goods.selectAll");
            for (Goods goods : list) {
                System.out.println(goods.getGoodsId()+":"+goods.getTitle());
            }
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectById() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Goods goods = sqlSession.selectOne("goods.selectById",2673);
            System.out.println(goods.getTitle());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectByPriceRange() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            Map map = new HashMap();
            map.put("min",100);
            map.put("max",500);
            map.put("count",10);
            List<Goods> list = sqlSession.selectList("goods.selectByPriceRange",map);
            for (Goods goods : list) {
                System.out.println(goods.getTitle());
            }
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectGoodsMap() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();

            List<Map> list = sqlSession.selectList("goods.selectGoodsMap");
            for (Map map : list) {
                System.out.println(map);
            }
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public  void testSelectGoodsDto() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            List<GoodsDTO> list = sqlSession.selectList("goods.selectGoodsDto");
            for (GoodsDTO map : list) {
                System.out.println(map.getGoods()+":"+map.getCategory().getCategoryName());
            }
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testInsertGoods() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession(false);
            Goods goods = new Goods();
            goods.setTitle("测试商品插入");
            goods.setSubTitle("测试副标题");
            goods.setCurrentPrice(200.f);
            goods.setIsFreeDelivery(1);
            goods.setOriginalCost(100.f);
            goods.setCategoryId(43);
            goods.setDiscount(100.f);
            //表示插入记录完成的个数
            int num = sqlSession.insert("goods.insertGoods",goods);
            //注意 手动提交事务
            sqlSession.commit();
            //新增的主键ID 并没有回填给 goods 对后续的数据操作存在影响 需要在mapper中配置selectKey 回填给goodsId
            System.out.println(goods.getGoodsId());
        }catch (Exception e){
            if (sqlSession != null){
                //出现错误后回滚事务
                sqlSession.rollback();
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testUpdateGoods() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession(false);
            Goods goods = sqlSession.selectOne("goods.selectById",739);
            goods.setTitle("测试商品修改");
            int num = sqlSession.update("goods.updateGoods",goods);
            //注意 手动提交事务
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null){
                //出现错误后回滚事务
                sqlSession.rollback();
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testDeleteGoods() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession(false);
            int num = sqlSession.delete("goods.deleteGoods",739);
            //注意 手动提交事务
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null){
                //出现错误后回滚事务
                sqlSession.rollback();
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void dynamicSql() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession(false);
            Map map = new HashMap();
//            map.put("categoryId",47);
//            map.put("currentPrice",500);
            List<Goods> goods = sqlSession.selectList("goods.dynamicSql",map);
            for (Goods good : goods) {
                System.out.println(good.getTitle());
            }
            //注意 手动提交事务
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null){
                //出现错误后回滚事务
                sqlSession.rollback();
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void dynamicSql2() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession(false);
            Map map = new HashMap();
            map.put("age",30);
            map.put("sex","男");
            List<Student> students = sqlSession.selectList("student.dynamicSql",map);
            for (Student student : students) {
                System.out.println(student.getName());
            }
            //注意 手动提交事务
            sqlSession.commit();
        }catch (Exception e){
            if (sqlSession != null){
                //出现错误后回滚事务
                sqlSession.rollback();
            }
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    /**
     * 测试sqlsession 一级缓存 默认开启
     * @throws Exception
     */
    @Test
    public void testLevelOneCache() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍
            Goods goods = sqlSession.selectOne("goods.selectById",2673);
            Goods goods1 = sqlSession.selectOne("goods.selectById",2673);
            //在同一个sqlSession 中执行相同的sql语句都是指向了同一个对象
            //1963862935:1963862935:1042786867 hashcode 也就验证了sqlSession执行相同的查询语句 将从缓存中的JVM内存中读取的数据
            System.out.println(goods.hashCode()+":"+goods1.hashCode());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }

        try {
            //一级缓存 生命周期太短了
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍
            Goods goods = sqlSession.selectOne("goods.selectById",2673);
            sqlSession.commit();//commit 会把当前的namespace下 所有缓存进行强制清空
            testInsertGoods();
            Goods goods1 = sqlSession.selectOne("goods.selectById",2673);
            //在同一个sqlSession 中执行相同的sql语句都是指向了同一个对象
            System.out.println(goods.hashCode()+":"+goods1.hashCode());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    /**
     * 测试二级缓存
     * 即便将SqlSession.close 关闭再次查询相同的SQL不会在执行SQL语句了 只要执行了一遍就会缓存
     * 下次再次执行直接从缓存中读取
     * Cache Hit Ratio [goods]: 0.5 为缓存的命中率 第一次存入缓存 第二次从缓存中读取
     */
    @Test
    public void testLevelTwoCache() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍 存入缓存
            Goods goods = sqlSession.selectOne("goods.selectById",2673);
            System.out.println(goods.hashCode());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }

        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID 先从缓存中查找 如果存在则从缓存中读取 不用在去执行SQL语句
            Goods goods = sqlSession.selectOne("goods.selectById",2673);
            System.out.println(goods.hashCode());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testOneToMore() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍 存入缓存
            List<Goods> list = sqlSession.selectList("goods.oneToMore");
            System.out.println(list.get(0).getTitle()+":"+list.get(0).getGoodsDetails().size());
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testMoreToOne() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍 存入缓存
            List<GoodsDetail> list = sqlSession.selectList("goodsDetail.moreToOne");
            //注意 association 会优先将查询到的goods_id优先传递给goods,
            for (GoodsDetail goodsDetail : list) {
                System.out.println(goodsDetail.getGdId()+":"+goodsDetail.getGoods().getTitle());
            }

        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testClasses() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍 存入缓存
            List<Classes> list = sqlSession.selectList("classes.selectClassesStudent","Class001");
            //注意 association 会优先将查询到的goods_id优先传递给goods,
            for (Classes classes : list) {
                System.out.println(classes.getClassno()+":"+classes.getStudents().size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testStudent(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //查询相同的ID sql语句只执行了一遍 存入缓存
            List<Student> list = sqlSession.selectList("student.selectByClass");
            //注意 association 会优先将查询到的goods_id优先传递给goods,
            for (Student student : list) {
                System.out.println(student.getName()+":"+student.getClasses().getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectPage(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //在查询语句前先配置分页数据 startPage会自动查询分页
            PageHelper.startPage(3,10);//从第二页开始 加载十条数据
            //在开始查询语句
            Page<Goods> page = (Page) sqlSession.selectList("goods.selectPage");
            System.out.println("总页数："+page.getPages());
            System.out.println("总记录数："+page.getTotal());
            System.out.println("当前页数:"+page.getPageNum());
            System.out.println("开始的行数："+page.getStartRow());
            System.out.println("结束的行数："+page.getEndRow());
            List<Goods> result = page.getResult();
            for (Goods goods : result) {
                System.out.println(goods.getTitle());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testBatchInsert(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //测试插入10000条数据
            long start = new Date().getTime();
            List list = new ArrayList();
            for (int i = 0; i < 10000; i++) {
                Goods goods = new Goods();
                goods.setTitle("测试插入："+i);
                goods.setSubTitle("测试商品:"+i);
                goods.setDiscount(200f);
                goods.setCategoryId(1);
                goods.setIsFreeDelivery(1);
                goods.setCurrentPrice(100f);
                goods.setOriginalCost(100f);
                list.add(goods);
            }
            sqlSession.insert("goods.batchInsert",list);
            long end = new Date().getTime();
            System.out.println("所用时间："+(end-start)+"ms");//7253ms
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testBatchInsert2(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            //测试插入10000条数据
            long start = new Date().getTime();
            List list = new ArrayList();
            for (int i = 0; i < 10000; i++) {
                Goods goods = new Goods();
                goods.setTitle("测试插入："+i);
                goods.setSubTitle("测试商品:"+i);
                goods.setDiscount(200f);
                goods.setCategoryId(1);
                goods.setIsFreeDelivery(1);
                goods.setCurrentPrice(100f);
                goods.setOriginalCost(100f);
                sqlSession.insert("goods.insertGoods",goods);
            }
            long end = new Date().getTime();
            System.out.println("所用时间："+(end-start)+"ms");//14178ms 一条一条的进行插入 所用的时间要比批量插入长很多
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testBatchDelete(){
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtils.openSession();
            List list = new ArrayList();
            list.add(1901);
            list.add(1902);
            list.add(1903);
            sqlSession.delete("goods.batchDelete",list);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }
}
