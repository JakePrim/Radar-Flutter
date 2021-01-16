import com.example.pojo.Account;
import com.example.service.AccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Test {
    @org.junit.Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-anno.xml");
        AccountService service = (AccountService) context.getBean("accountService");
        List<Account> accounts = service.findAll();
        System.out.println(accounts);
        context.close();
    }
}
