import com.alibaba.dubbo.config.annotation.Reference;
import com.sfl.mapper.UserDao;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import com.sfl.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @program: Edu
 * @Description: 测试用户相关
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-04 16:09
 * @PackageName: PACKAGE_NAME
 * @ClassName: UserTest.java
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service.xml"})
public class UserServiceTest {

    @Reference
    private UserService userService;

    @Test
    public void testLogin() {
//        User dto = userService.login("110", "123");
//        System.out.println(dto);
    }
}
