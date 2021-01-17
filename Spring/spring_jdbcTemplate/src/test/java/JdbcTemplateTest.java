import com.prim.config.SpringConfig;
import com.prim.domain.Account;
import com.prim.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class JdbcTemplateTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void test() {
        Account account = new Account();
        account.setName("prim");
        account.setMoney(1000d);
        accountService.save(account);

        List<Account> accounts = accountService.findAll();
        System.out.println(accounts);

    }

    @Test
    public void testTransfer() {
        accountService.transfer("tom", "jerry", 100d);
    }
}
