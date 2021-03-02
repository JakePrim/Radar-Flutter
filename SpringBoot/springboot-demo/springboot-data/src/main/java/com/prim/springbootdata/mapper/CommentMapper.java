package com.prim.springbootdata.mapper;

import com.prim.springbootdata.pojo.Comment;
import org.apache.ibatis.annotations.Select;

/**
 * @program: springboot-demo
 * @Description: mapper 通过注解进行实现
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-01 21:50
 * @PackageName: com.prim.springbootdata.mapper
 * @ClassName: CommentMapper.java
 **/
public interface CommentMapper {
    @Select("select * from t_comment where id=#{id}")
    Comment findById(String id);
}
