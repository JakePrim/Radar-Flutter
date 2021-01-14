package com.example.mapper;

import com.example.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有用户
     */
    @Select(value = "select * from user")
    //执行的SQL语句
    List<User> findAll();

    /**
     * 添加用户
     */
    @Insert("insert into user(username,birthday,sex,address) values(#{username},#{birthday},#{sex},#{address})")
    void save(User user);

    /**
     * 更新用户
     */
    @Update("update user set username=#{username},sex=#{sex} where id=#{id}")
    void update(User user);

    /**
     * 删除用户
     */
    @Delete("delete from user where id=#{id}")
    void delete(Integer id);

    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    /**
     * 查询所有用户及关联的订单信息
     *
     * @return
     */
    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "username", column = "username"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "birthday", column = "birthday"),
            @Result(property = "address", column = "address"),
            @Result(property = "ordersList", javaType = List.class,
                    many = @Many(select = "com.example.mapper.OrderMapper.findByUid"), column = "id")
    })
    List<User> findAllWithOrders();
}
