package com.edu.service;

import com.edu.pojo.PromotionSpace;

import java.util.List;

public interface PromotionSpaceService {
    List<PromotionSpace> findAllPromotionSpace();

    void savePromotionSpace(PromotionSpace promotionSpace);

    PromotionSpace findPromotionSpaceById(Integer id);

    void updatePromotionSpace(PromotionSpace promotionSpace);

}
