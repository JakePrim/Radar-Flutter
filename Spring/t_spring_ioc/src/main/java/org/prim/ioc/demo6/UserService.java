package org.prim.ioc.demo6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author prim
 * 传统 <bean id="" class""></bean>
 */
//@Component("userService")
@Service("userService")
public class UserService {
    public String sayHello(String name) {
        return "hello:" + name;
    }

    @Value("米饭")
    private String someting;

//    @Autowired
//    @Qualifier("userDao") //必须匹配UserDao的设置的名称
    @Resource(name = "userDao")
    private UserDao userDao;

    public void eat() {
        System.out.println("eat:" + someting);
    }

    public void save() {
        System.out.println("Service中保存用户的方法");
        userDao.save();
    }
}
