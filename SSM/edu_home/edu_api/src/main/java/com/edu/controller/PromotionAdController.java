package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.PromotionAd;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.vo.PromotionAdVo;
import com.edu.service.PromotionAdService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController extends BaseController {

    @Autowired
    private PromotionAdService promotionAdService;

    /**
     * 广告列表分页查询
     * 注意：GET请求无需添加 请求体：@RequestBody
     *
     * @return
     */
    @GetMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVo promotionAdVo) {
        PageInfo<PromotionAd> promotionAdByPage = promotionAdService.findAllPromotionAdByPage(promotionAdVo);
        ResponseResult responseResult = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), promotionAdByPage);
        return responseResult;
    }

    /**
     * 广告图片上传
     */
    @PostMapping("/PromotionAdUpload")
    public ResponseResult promotionAdUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Map<String, String> map = fileUpload("upload/ad/", file, request);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return result;
    }

    @PostMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        if (promotionAd.getId() != null) {
            promotionAdService.updatePromotionAd(promotionAd);
        } else {
            promotionAdService.savePromotionAd(promotionAd);
        }
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return result;
    }

    @GetMapping("/findPromotionAdById")
    public ResponseResult findPromotionAdById(Integer id) {
        PromotionAd promotionAd = promotionAdService.findPromotionAdById(id);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), promotionAd);
        return result;
    }

    @GetMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(Integer id, Integer status) {
        promotionAdService.updatePromotionAdStatus(id, status);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
        return result;
    }

}
