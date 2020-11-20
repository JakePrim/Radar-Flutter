package com.prim.biz;

import com.prim.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryBiz {
    List<Category> selectAll();

    /**
     * 根据某个ID查询分类
     * @param id
     * @return
     */
    Category selectById(@Param("id") int id);

    int addCategory(Category category);

    int delete(@Param("id") int id);
}
