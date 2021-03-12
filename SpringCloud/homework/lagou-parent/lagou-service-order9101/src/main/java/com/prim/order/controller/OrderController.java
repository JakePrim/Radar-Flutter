package com.prim.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.order.fegin.GoodsFeign;
import com.prim.common.pojo.Goods;
import com.prim.common.pojo.Order;
import com.prim.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 18:59
 * @PackageName: com.prim.order.controller
 * @ClassName: OrderController.java
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/query/{id}")
    public Order queryById(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        return order;
    }

    @GetMapping("/query/{page}/{pageSize}/{status}")
    public Map queryPageStatus(
            @PathVariable(name = "page") Integer page,
            @PathVariable(name = "pageSize") Integer pageSize,
            @PathVariable(name = "status") Integer status
    ) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        IPage<Order> orderIPage = orderService.findPageOrderStatus(orderPage, status);
        Map resultMap = new HashMap();
        System.out.println("queryPageStatus:" + orderIPage.getRecords().size());
        resultMap.put("list", orderIPage.getRecords());
        resultMap.put("total", orderIPage.getTotal());
        return resultMap;
    }

    @GetMapping("/queryTime/{page}/{pageSize}/{st}/{et}")
    public Map queryPageTime(
            @PathVariable(name = "page") Integer page,
            @PathVariable(name = "pageSize") Integer pageSize,
            @PathVariable(name = "st") String startTime,
            @PathVariable(name = "et") String endTime
    ) {
        Page<Order> orderPage = new Page<>(page, pageSize);
        IPage<Order> orderIPage = orderService.selectCreateTimePageVo(orderPage, startTime, endTime);
        Map resultMap = new HashMap();
        resultMap.put("list", orderIPage.getRecords());
        resultMap.put("total", orderIPage.getTotal());
        return resultMap;
    }

    @Autowired
    private GoodsFeign goodsFeign;

    /**
     * 根据订单编号查询商品列表
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderGoods/{orderId}")
    public List<Goods> getOrderGoods(@PathVariable Integer orderId) {
        List<Goods> goods = goodsFeign.queryByOrderId(orderId);
        return goods;
    }

    /**
     * 测试负载均衡是否正常 熔断是否降级正常
     */
    @GetMapping("/port")
    public String getPort() {
        return goodsFeign.getPort();
    }
}
