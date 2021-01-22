package com.edu.service;

import com.edu.pojo.PromotionAd;
import com.edu.pojo.vo.PromotionAdVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PromotionAdService {
    PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVo promotionAdVo);

    void savePromotionAd(PromotionAd promotionAd);

    void updatePromotionAd(PromotionAd promotionAd);

    PromotionAd findPromotionAdById(Integer id);

    void updatePromotionAdStatus(Integer id, Integer status);
}
