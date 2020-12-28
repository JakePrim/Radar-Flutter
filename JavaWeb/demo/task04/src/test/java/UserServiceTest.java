import com.example.task04.dao.UserDaoImp;
import com.example.task04.model.User;
import com.example.task04.service.UserService;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    @Test
    public void testService() {
        UserService userService = new UserService();
        User admin = userService.userLoginService(new User("admin", "123456"));
        System.out.println(admin);
    }
}