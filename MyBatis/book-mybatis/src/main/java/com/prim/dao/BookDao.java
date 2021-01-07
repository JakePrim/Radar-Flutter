package com.prim.dao;

import com.prim.pojo.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author prim
 */
public interface BookDao {
    @Select("select b.*,c.name as cname from book as b left join category as c on b.category_id = c.id where b.category_id = #{categoryId}")
    @Results(id = "all", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "category.name", column = "cname")
    })
    List<Book> selectBookByCid(@Param("categoryId") int categoryId);

    @Insert("<script>" +
            "insert into book(category_id,name,level,price,img_path,create_time,update_time) values" +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.categoryId},#{item.name},#{item.level},#{item.price},#{item.imgPath},#{item.createTime},#{item.updateTime})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int batchBook(List<Book> books);

    @Select("select b.*,c.name as cname from book as b left join category as c on b.category_id = c.id")
    @ResultMap("all")
    List<Book> selectAll();
}
