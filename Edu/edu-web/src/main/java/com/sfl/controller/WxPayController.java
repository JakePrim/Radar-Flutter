package com.sfl.controller;

import com.sfl.pojo.ResultDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: edu-web
 * @Description:微信支付相关业务
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-27 20:18
 * @PackageName: com.sfl.controller
 * @ClassName: WxPayController.java
 **/
@RestController
@RequestMapping("/order")
public class WxPayController {
    @GetMapping("/createCode")
    public Object createCode(String courseid, String coursename, String price) {
        return null;
    }
}
