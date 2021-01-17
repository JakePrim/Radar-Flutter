import com.example.AccountService;
import com.example.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class TestSpringAopXml {

    @Autowired
    private AccountService accountService;

    @Test
    public void test() {
        accountService.transfer();
    }
}
