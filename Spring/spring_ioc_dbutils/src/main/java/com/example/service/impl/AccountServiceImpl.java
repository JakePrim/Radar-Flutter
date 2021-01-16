package com.example.service.impl;

import com.example.dao.AccountDao;
import com.example.pojo.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

//@Service == <bean> 标签的配置，默认的id  class = AccountServiceImpl
@Service("accountService")//value == id 配置就是id
//@Scope("singleton")//作用域配置为单例
public class AccountServiceImpl implements AccountService {
    //通过Spring 注入AccountDao实例
//    @Autowired //根据类型进行注入
//    @Qualifier("accountDao")//根据类型匹配到多个实例时，会根据设置的值进行二次匹配
    @Resource(name = "accountDao") // 根据name值，直接从IOC容器中进行匹配
    private AccountDao accountDao;

    @Value("注入普通属性")
    private String str1;

    @Value("${jdbc.driverClassName}") //注入properties文件中的配置,因为我们已经在applicationContext.xml中加载了该文件可以直接使用
    private String driver;

    @PostConstruct
    private void init() {
        System.out.println("方法初始化");
    }

    @PreDestroy
    private void destroy() {
        System.out.println("方法销毁");
    }

    @Override
    public List<Account> findAll() {
        System.out.println(str1);//注入普通属性
        System.out.println(driver);//com.mysql.jdbc.Driver
        return accountDao.findAll();
    }

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id);
    }

    @Override
    public void save(Account account) {
        accountDao.save(account);
    }

    @Override
    public void update(Account account) {
        accountDao.update(account);
    }

    @Override
    public void delete(Integer id) {
        accountDao.delete(id);
    }
}
