package com.example.mapper;

import com.example.domain.Orders;
import com.example.domain.User;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface OrderMapper {
    /**
     * 查询所有订单 同时查询订单所属的用户信息
     *
     * @return
     */
    @Select("select * from orders")
    @Results({
            @Result(property = "id", column = "id", id = true),
            @Result(property = "ordertime", column = "ordertime"),
            @Result(property = "total", column = "total"),
            @Result(property = "uid", column = "uid"),
            @Result(property = "user", javaType = User.class,
                    one = @One(select = "com.example.mapper.UserMapper.findById", fetchType = FetchType.EAGER), column = "uid")
    })
    List<Orders> findAllWithUser();

    @Select("select * from orders where uid=#{uid}")
    List<Orders> findByUid(Integer uid);
}
