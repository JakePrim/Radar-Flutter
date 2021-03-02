package com.sfl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.UserCourseOrder;
import com.sfl.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: edu-web
 * @Description: 订单api
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 09:55
 * @PackageName: com.sfl.controller
 * @ClassName: OrderController.java
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ResultDTO saveOrder(@RequestBody UserCourseOrder userCourseOrder) {
        int row = orderService.saveOrder(userCourseOrder);
        if (row == 0) {
            return ResultDTO.createError("下单失败");
        } else {
            return ResultDTO.createSuccess("下单成功");
        }
    }

    @GetMapping("/updateOrder/{orderNo}/{status}")
    public ResultDTO updateOrder(@PathVariable("orderNo") String orderNo, @PathVariable("status") Integer status) {
        Integer row = orderService.updateOrder(orderNo, status);
        if (row == 0) {
            return ResultDTO.createError("订单状态更新失败");
        } else {
            return ResultDTO.createSuccess("订单状态更新成功");
        }
    }

    @GetMapping("/deleteOrder/{orderNo}")
    public ResultDTO deleteOrder(@PathVariable("orderNo") String orderNo) {
        Integer row = orderService.deleteOrder(orderNo);
        if (row == 0) {
            return ResultDTO.createError("删除订单失败");
        } else {
            return ResultDTO.createSuccess("删除订单成功");
        }
    }

    @GetMapping("/findOrderAll/{userId}")
    public ResultDTO findOrderAll(@PathVariable("userId") String userId) {
        List<UserCourseOrder> orders = orderService.findOrderAll(userId);
        return ResultDTO.createSuccess("查询成功", orders);
    }



}
