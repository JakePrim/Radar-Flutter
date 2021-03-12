package com.prim.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.prim.common.pojo.Goods;
import com.prim.common.pojo.Order;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 16:55
 * @PackageName: com.prim.order.mapper
 * @ClassName: OrderMapper.java
 **/
public interface OrderMapper extends BaseMapper<Order> {
    Order findById(Integer id);

    /**
     * 注意自定义分页xml 需要配置分页插件否则无效
     *
     * @param page
     * @param status
     * @return
     */
    IPage<Order> selectPageVo(Page<Order> page, Integer status);

    /**
     * 订单创建时间范围 分页查询
     */
    IPage<Order> selectCreateTimePageVo(Page<Order> page, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
