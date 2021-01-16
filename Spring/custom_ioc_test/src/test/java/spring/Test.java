package spring;

import com.example.ioc.dao.IUserDao;
import com.example.ioc.service.IUserService;
import com.example.ioc.service.impl.UserServiceImpl;
import com.example.ioc.utils.BeanFactory;

public class Test {
    @org.junit.Test
    public void testCustom() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        IUserService service = new UserServiceImpl();
        service.save();
    }
}
