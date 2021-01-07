import com.prim.pointcut.CustomerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext2.xml")
public class AdviceTest {
    @Resource(name = "coustomerDaoProxy") //使用代理类的bean
    private CustomerDao customerDao;

    @Test
    public void test(){
        customerDao.find();
        customerDao.save();
        customerDao.delete();
        customerDao.update();
    }
}
