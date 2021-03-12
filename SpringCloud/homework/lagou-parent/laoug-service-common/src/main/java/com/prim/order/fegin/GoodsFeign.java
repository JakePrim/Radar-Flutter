package com.prim.order.fegin;

import com.prim.order.fegin.fallback.GoodsFallback;
import com.prim.common.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @program: lagou-cloud-eureka-9301
 * @Description: 注意接口的方法请求的 url 必须要是全路径因为feign通过服务名拿到地址例如：127.0.0.1:9200/XXX/xxx 需要跟上good微服务的Controller中的mapping
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-11 22:49
 * @PackageName: com.prim.order.fegin
 * @ClassName: GoodsFegin.java
 **/
@FeignClient(name = "lagou-service-goods", fallback = GoodsFallback.class)
public interface GoodsFeign {
    /**
     * 注意 goods的微服务集群必须都要这个方法 否则负载均衡出现问题会导致整个服务无法访问
     *
     * @param id
     * @return
     */
    @GetMapping("/goods/queryByOrderId/{id}")
    List<Goods> queryByOrderId(@PathVariable Integer id);

    @GetMapping("/goods/port")
    String getPort();
}
