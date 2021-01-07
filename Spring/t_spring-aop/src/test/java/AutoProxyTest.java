import com.prim.autoproxy.CustomerDao;
import com.prim.autoproxy.ProductDao;
import com.prim.autoproxy.StudentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext3.xml")
public class AutoProxyTest {

    @Resource(name = "customerDao")
    private CustomerDao customerDao;

    @Resource(name = "productDao")
    private ProductDao productDao;

    @Resource(name = "studentDao")
    private StudentDao studentDao;

    @Test
    public void test() {
        customerDao.find();
        customerDao.save();
        customerDao.delete();
        customerDao.update();

        productDao.save();
    }

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
