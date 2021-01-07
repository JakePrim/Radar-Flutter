package com.jakeprim.biz;

import com.jakeprim.dto.CategoryDTO;
import com.jakeprim.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author prim
 */
public interface CategoryBiz {
    /**
     * 添加
     *
     * @param categories
     * @return
     */
    int add(List<Category> categories);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 根据某个分类，查询该分类下的所有子类
     *
     * @param id
     * @return
     */
    Category selectAll(int id);

    /**
     * 获取顶层分类 下的所有分类
     * @return
     */
    Category getRoot();

    boolean selectByTitleCount(String title);
}
