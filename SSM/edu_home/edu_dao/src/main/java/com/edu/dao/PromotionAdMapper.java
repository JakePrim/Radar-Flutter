package com.edu.dao;

import com.edu.pojo.PromotionAd;

import java.util.List;

public interface PromotionAdMapper {

    /**
     * 广告分页查询
     *
     * @return
     */
    List<PromotionAd> findAllPromotionAdByPage();

    /**
     * 新增广告
     */
    void savePromotionAd(PromotionAd promotionAd);

    void updatePromotionAd(PromotionAd promotionAd);

    PromotionAd findPromotionAdById(Integer id);

    void updatePromotionAdStatus(PromotionAd promotionAd);
}
