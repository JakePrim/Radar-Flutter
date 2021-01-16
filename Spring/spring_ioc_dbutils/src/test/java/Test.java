import com.example.SpringConfig;
import com.example.pojo.Account;
import com.example.service.AccountService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//SpringJUnit4ClassRunner是spring提供的作为junit运行环境的类
@RunWith(SpringJUnit4ClassRunner.class)
//设置核心配置类
@ContextConfiguration(classes = {SpringConfig.class})
//设置核心配置文件
//@ContextConfiguration({"classpath:applicationContext.xml"})
public class Test {
    @Autowired //注入依赖
    AccountService accountService;

    @org.junit.Test
    public void test() {
        //XML的方式 加载核心配置文件
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-anno.xml");

        //纯注解的方式 加载核心配置类
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//        AccountService service = (AccountService) context.getBean("accountService");
        List<Account> accounts = accountService.findAll();
        System.out.println(accounts);
    }
}
