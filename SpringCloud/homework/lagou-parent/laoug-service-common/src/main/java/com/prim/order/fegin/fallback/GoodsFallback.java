package com.prim.order.fegin.fallback;

import com.prim.order.fegin.GoodsFeign;
import com.prim.common.pojo.Goods;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 22:50
 * @PackageName: com.prim.order.fegin.fallback
 * @ClassName: GoodsFallbacl.java
 **/
@Component
public class GoodsFallback implements GoodsFeign {

    @Override
    public List<Goods> queryByOrderId(Integer id) {
        return new ArrayList<>();//[]
    }

    @Override
    public String getPort() {
        return "服务降级";
    }
}
