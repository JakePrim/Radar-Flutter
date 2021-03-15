import com.lagou.mapper.IUserMapper;
import com.lagou.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisTest {

  /**
   * 传统方式
   * @throws IOException
   */
  @Test
  public void test1() throws IOException {

    //mybatis的初始化的工程

    // 1. 读取配置文件，读成字节输入流，注意：现在还没解析
    InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

    // 2. 解析配置文件，封装Configuration对象   创建DefaultSqlSessionFactory对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);


    //执行SQL的流程

    // 3. 生产了DefaultSqlsession实例对象   设置了事务不自动提交  完成了executor对象的创建
    SqlSession sqlSession = sqlSessionFactory.openSession();

    // 4.(1)根据statementid来从Configuration中map集合中获取到了指定的MappedStatement对象
    //   (2)将查询任务委派了executor执行器
    User user =  sqlSession.selectOne("com.lagou.mapper.IUserMapper.findById",1);
    System.out.println(user);
    User user2 =  sqlSession.selectOne("com.lagou.mapper.IUserMapper.findById",1);
    System.out.println(user2);

    // 5.释放资源
    sqlSession.close();

  }

  /**
   * mapper代理方式
   */
  @Test
  public void test2() throws IOException {

    InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
    SqlSession sqlSession = factory.openSession();

    // 使用JDK动态代理对mapper接口产生代理对象
    IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);

    //代理对象调用接口中的任意方法，执行的都是动态代理中的invoke方法
    List<User> all = mapper.findAll();
    for (User user : all) {
      System.out.println(user);
    }

  }

  /**
   * mybatis二级缓存效果测试
   */
  @Test
  public void test3() throws IOException {

    InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

    SqlSession sqlSession1 = factory.openSession();
    SqlSession sqlSession2 = factory.openSession();

    User user1 = sqlSession1.selectOne("com.lagou.mapper.IUserMapper.findById", 1);
    System.out.println(user1);

    sqlSession1.commit();

    User user = new User();
    user.setId(1);
    user.setUsername("jack");
    // 增删改会清空二级缓存
    sqlSession1.update("com.lagou.mapper.IUserMapper.updateById",user);


    User user2 = sqlSession2.selectOne("com.lagou.mapper.IUserMapper.findById", 1);
    System.out.println(user2);

  }


  /**
   * mybatis嵌套效果测试
   */
  @Test
  public void test4() throws IOException {

    InputStream inputStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);

    SqlSession sqlSession = factory.openSession();

    User user = sqlSession.selectOne("com.lagou.mapper.IUserMapper.findById", 1);

    System.out.println(user.getUsername());
    System.out.println(user.getOrderList());

  }





}







