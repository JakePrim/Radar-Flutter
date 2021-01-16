package test;

import com.prim.dao.UserDao;
import com.prim.service.UserService;
import com.prim.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class SpringTest {
    @Test
    public void userDaoTest() {
        //1. 获取到spring上下文对象，借助上下文对象可以获取到IOC容器中的bean对象
        // 这句代码其实就是：
        // - 加载配置文件
        // - 解析配置文件
        // - 创建bean对象
        // - 在IOC容器中存储
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        //根据bean id 获取bean
        //直接从IOC容器中取出bean对象
//        UserDao userDao = (UserDao) applicationContext.getBean("userDao");

        //根据类型 获取bean
//        UserDao userDao = applicationContext.getBean(UserDao.class);

        //根据bean id 和 class 一起获取bean
        UserDao userDao = applicationContext.getBean("userDao2", UserDao.class);
        userDao.save();
    }

    @Test
    public void test2() {
        //BeanFactory 是核心接口
        //这句代码执行的时候：不会创建bean对象 存到IOC容器中
        BeanFactory xmlBeanFactory = new XmlBeanFactory(new ClassPathResource("applicationContext.xml"));
        //在调用getBean的才真正创建bean对象 存到容器中，再根据key取出bean对象
        UserDao userDao = (UserDao) xmlBeanFactory.getBean("userDao");
        userDao.save();
    }

    /**
     * 测试singleton 属性
     */
    @Test
    public void testSingleton() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = applicationContext.getBean("userDao", UserDao.class);
        UserDao userDao2 = applicationContext.getBean("userDao", UserDao.class);
        System.out.println(userDao == userDao2);//true 单例的
        userDao.save();
        applicationContext.close();
    }

    @Test
    public void testFactoryUserDao() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("factoryUserDao");
        userDao.save();
    }

    @Test
    public void testDynamicFactoryUserDao() {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("dynamicUserDao");
        userDao.save();
    }

    /**
     * 业务层解耦
     */
    @Test
    public void testService() {
//        UserService service = new UserServiceImpl();
        //
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = (UserService) applicationContext.getBean("userService");
        service.save();
    }

    @Test
    public void testService2() {
        //取出Service的实例
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService service = (UserService) applicationContext.getBean("userService");
        service.save();
    }
}
