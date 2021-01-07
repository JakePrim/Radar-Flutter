package com.jakeprim.dao;

import com.jakeprim.dto.CategoryDTO;
import com.jakeprim.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author prim
 */
public interface CategoryDao {
    /**
     * 批量插入
     *
     * @param list
     * @return 返回成功插入的数量
     */
    @Insert("<script>" +
            "insert into category(title,pid,info) values" +
            "<foreach collection='list' item='item' index='index' separator=','>" +
            "(#{item.title},#{item.pid},#{item.info})" +
            "</foreach>" +
            "</script>")
    int batchInsert(List<Category> list);

    /**
     * 删除数据
     *
     * @param id 根据主键ID进行删除
     * @return 返回删除成功的数量
     */
    @Delete("delete from category where id = #{id}")
    int delete(@Param("id") int id);

    /**
     * 批量删除
     *
     * @param id
     * @return
     */
    @Delete("<script>" +
            "delete from category where id in" +
            "<foreach collection='list' item='item' index='index' open='(' close=')' separator=','>" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int batchDelete(@Param("id") int id);

    /**
     * 查询某个分类下的所有子类以及子类下的分类
     *
     * @param id
     * @return
     */
    @Select("select * from category where id=#{id}")
    @Results(id = "all", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "title", column = "title"),
            @Result(property = "pid", column = "pid"),
            @Result(property = "info", column = "info"),
            //关联所有的子类 根据查询到到id,查询所有的子类
            @Result(property = "children", column = "id", many = @Many(select = "selectById"))
    })
    Category selectCategory(@Param("id") int id);

    /**
     * 查询某个分类下的子类
     *
     * @param pid
     * @return 返回子类列表
     */
    @Select("select * from category where pid=#{pid}")
    @ResultMap("all")
    List<Category> selectById(@Param("pid") int pid);//查询子类

    /**
     * 查询是否存在相同到title
     * @param title
     * @return
     */
    @Select("select count(*) from category where title=#{title}")
    int selectByTitleCount(@Param("title") String title);

}
