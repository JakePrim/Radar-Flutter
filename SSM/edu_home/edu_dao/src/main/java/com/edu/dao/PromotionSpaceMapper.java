package com.edu.dao;

import com.edu.pojo.PromotionSpace;

import java.util.List;

public interface PromotionSpaceMapper {
    List<PromotionSpace> findAllPromotionSpace();

    /**
     * 新增广告位
     *
     * @param promotionSpace
     */
    void savePromotionSpace(PromotionSpace promotionSpace);

    /**
     * 更新广告位 名称
     *
     * @param promotionSpace
     */
    void updatePromotionSpace(PromotionSpace promotionSpace);

    /**
     * 根据广告位id 查询广告位
     *
     * @param id
     * @return
     */
    PromotionSpace findPromotionSpaceById(Integer id);
}
