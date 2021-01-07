import com.prim.pointcut.autoproxy.CustomerDao;
import com.prim.pointcut.autoproxy.ProductDao;
import com.prim.pointcut.autoproxy.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext4.xml")
public class PointAutoProxyTest {

    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    @Resource(name = "productDao")
    private ProductDao productDao;

    @Resource(name = "studentDao")
    private StudentDao studentDao;

    @Test
    public void pointcutTest() {
        customerDao.find();
        customerDao.save();
        customerDao.delete();
        customerDao.update();

        productDao.save();

        studentDao.save();
    }
}
