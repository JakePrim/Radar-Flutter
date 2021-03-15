package com.prim.mybatisplusspringboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.mybatisplusspringboot.mapper.UserMapper;
import com.prim.mybatisplusspringboot.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.soap.Addressing;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class MybatisPlusSpringbootApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            System.out.println(user);
        }

        List<User> users2 = userMapper.findAll();//注意使用xml的方式 并不能使mp的TableFiled注解生效
        for (User user : users2) {
            System.out.println(user);
        }
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("jake1");
        user.setPassword("123");
        user.setBirth("2021-1-1");
        int row = userMapper.insert(user);
        System.out.println(row);

        System.out.println("id值为：" + user.getId());
    }

    @Test
    public void testUpdate() {

        User user = new User();
//        user.setId(3);
        user.setUsername("jake");

//        int i = userMapper.updateById(user);

        //更新的条件
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", "jake");

        //直接设置更新的条件和内容
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", 3);
        updateWrapper.set("username", "jake");


        userMapper.update(null, updateWrapper);

//        System.out.println(user.getUsername());
    }

    @Test
    public void testDelete() {
        int row = userMapper.deleteById(3);
        System.out.println(row);
    }

    @Test
    public void testDeleteByMap() {
        //将map设置为了删除的条件 多个为and关系
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "jake");

        userMapper.deleteByMap(map);
    }

    @Test
    public void testDeleteWapper() {
        //推荐这种方式
        User user = new User();
        user.setUsername("jake");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);

//        wrapper.eq("id", 2);
//        wrapper.eq("username", "tom");
        userMapper.delete(wrapper);
    }

    @Test
    public void testDeleteBatch() {
        userMapper.deleteBatchIds(Arrays.asList(6, 7));
    }

    @Test
    public void testSelectBatchIds() {
        //批量查询
        List<User> users = userMapper.selectBatchIds(Arrays.asList(3, 4));
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 3);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查询总记录数
        wrapper.gt("id", 3);//id > 3的总记录数
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    @Test
    public void testSelectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查询总记录数
        wrapper.gt("id", 3);//id > 3的总记录数
        List<User> users = userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectPage() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //查询总记录数
        wrapper.gt("id", 1);//id > 3的总记录数

        Page<User> page = new Page<>(1, 2);

        IPage<User> iPage = userMapper.selectPage(page, wrapper);
        System.out.println(iPage.getPages());
        System.out.println(iPage.getTotal());
        List<User> records = iPage.getRecords();
        for (User record : records) {
            System.out.println(record);
        }
    }

    /**
     * 测试条件构建器：allEq
     */
    @Test
    public void testAllEq() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", "jake");
        map.put("id", 3);
        map.put("password", null);

//        queryWrapper.allEq(map); // where username = jake and id = 3 and password is null

//        queryWrapper.allEq(map, false);// where username = jake and id = 3 false表示为忽略value为null的值 不作为查询条件

//        queryWrapper.allEq(false, map, true);// SELECT id,username,birthday AS birth FROM tb_user  当condition为false不会将map作为条件查询

        queryWrapper.allEq((k, v) -> !k.equals("username"), map);// map集合如果匹配到key为username则不会将username当做查询条件。
        //SELECT id,username,birthday AS birth FROM tb_user WHERE password IS NULL AND id = ?
//        queryWrapper.allEq((k, v) -> k.equals("username"), map); // 表示只根据map集合中的username作为查询条件
        //SELECT id,username,birthday AS birth FROM tb_user WHERE username = ?
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
            //User(id=3, username=jake, password=null, birth=2021-1-1, address=null)
        }
    }

    /**
     * 测试条件构造器的基本比较操作
     * eq =
     * ne <>
     * gt >
     * ge >=
     * lt <
     * le <=
     * between： between 值1 and 值2
     * in  字段in(....)
     */
    @Test
    public void testWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "jake")
                .ge("birthday", "2020-1-1")
        ;
        //SELECT id,username,birthday AS birth FROM tb_user WHERE username = ? AND birthday >= ?
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试条件构造器的模糊查询
     * like : like "%x%"
     * notLike: not like "%x%"
     * likeLeft: like "%x"
     * likeRight: like "x%"
     */
    @Test
    public void testLike() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", "jake");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 逻辑查询
     */
    @Test
    public void testWrapper2() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("birthday");//SELECT id,username,birthday AS birth FROM tb_user ORDER BY birthday DESC
        queryWrapper.eq("username", "jake")
                .or()
                .eq("birthday", "2021-1-1")
                .select("username");//只查询 username 字段 SELECT username FROM tb_user WHERE username = ? OR birthday = ? ORDER BY birthday DESC
        //SELECT id,username,birthday AS birth FROM tb_user WHERE username = ? OR birthday = ? ORDER BY birthday DESC
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     *
     */

}
