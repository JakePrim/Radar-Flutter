import com.example.factory.CglibProxyFactory;
import com.example.factory.JdkProxyFactory;
import com.example.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class AccountTest {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private JdkProxyFactory jdkProxyFactory;
//
//    @Autowired
//    private CglibProxyFactory cglibProxyFactory;

    @Test
    public void test() {
//        accountService.transfer("tom", "jerry", 100d);
        //代理对象
//        AccountService serviceProxy = jdkProxyFactory.createAccountServiceProxy();
//        AccountService serviceProxy = cglibProxyFactory.createProxyAccountService();
        accountService.transfer("tom", "jerry", 100d);
    }
}
