import com.prim.spring.aop.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringAopTest {
    //@Resource(name = "studentDao") 没有增强的操作
    @Resource(name = "studentDaoProxy") //设置前置增强类
    private StudentDao studentDao;

    @Test
    public void test1() {
        studentDao.find();
        studentDao.save();
        studentDao.update();
        studentDao.delete();
    }
}
