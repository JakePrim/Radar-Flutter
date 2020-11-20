package com.prim.dao;

import com.prim.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author prim
 */
public interface CategoryDao {
    /**
     * 查询所有分类
     * @return
     */
    @Select("select * from category")
    List<Category> selectAll();

    /**
     * 根据某个ID查询分类
     * @param id
     * @return
     */
    @Select("select * from category where id = #{id}")
    Category selectById(@Param("id") int id);

    @Insert("insert into category(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    int addCategory(Category category);

    @Delete("delete from category where id = #{id}")
    int delete(@Param("id") int id);
}
