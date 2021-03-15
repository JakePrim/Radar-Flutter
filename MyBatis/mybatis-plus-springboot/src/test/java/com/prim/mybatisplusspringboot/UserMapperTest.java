package com.prim.mybatisplusspringboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prim.mybatisplusspringboot.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @program: MyBatis-PlusDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 19:12
 * @PackageName: com.prim.mybatisplusspringboot
 * @ClassName: UserMapperTest.java
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    /**
     * 在AR模式下 完成根据主键查询
     */
    @Test
    public void testARSelectById() {
        User user = new User();
        user.setId(3);

        User selectById = user.selectById();
        System.out.println(selectById);
    }

    @Test
    public void testARInsert() {
        User user = new User();
        user.setUsername("墨竹");
        user.setPassword("123");
        user.setBirth("2020-1-12");

        boolean b = user.insertOrUpdate();//如果没有id值则新增操作，如果存数据库中存在id值则做更新操作
        System.out.println(b);
    }

    @Test
    public void testARUpdate() {

        User user = new User();
        //先执行查询操作
        User user1 = user.selectById(4);
        user.setId(4);
        user.setUsername("mozhu11");
        user.setPassword("1231");
        user.setBirth("2020-1-12");
        //设置查询到的version
        user.setVersion(user1.getVersion());//oldVersion


        boolean b = user.updateById();
        System.out.println(b);
    }

    @Test
    public void testARDelete() {
        User user = new User();
        user.setId(9);
//        boolean b = user.deleteById(9);
        boolean b = user.deleteById();
        System.out.println(b);
    }

    @Test
    public void testARSelectWrapper() {
        User user = new User();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("birthday", "2020-1-1");
        List<User> users = user.selectList(wrapper);
        for (User user1 : users) {
            System.out.println(user1);
        }
    }
}
