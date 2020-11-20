import com.prim.ProductDao;
import com.prim.UserDao;
import com.prim.UserDaoImpl;
import com.prim.aop.AopImpl;
import com.prim.aop.MyCglibProxy;
import org.junit.Test;

public class AopTest {
    @Test
    public void testCommon() {
        //目标对象的实例
        UserDao userDao = new UserDaoImpl();
        //通过代理对象调用方法
        AopImpl aop = new AopImpl(userDao);
        UserDao proxy = (UserDao) aop.createProxy();
        proxy.save();
        proxy.update();
//        System.out.println("AopTest.testCommon" + proxy1.getClass());
//        ProductDao proxy = (ProductDao) aop.createProxy();
//        proxy.save();//在save之前进行权限校验
    }

    @Test
    public void testCglib() {
        ProductDao productDao = new ProductDao();
        ProductDao proxy = (ProductDao) new MyCglibProxy(productDao).createProxy();
        proxy.save();
    }

    @Test
    public void testSpringAop1() {

    }
}
