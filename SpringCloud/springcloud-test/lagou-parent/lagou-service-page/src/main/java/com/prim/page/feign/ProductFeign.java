package com.prim.page.feign;

import com.prim.common.pojo.Products;
import com.prim.page.fallback.ProductFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @program: springclouddemo
 * @Description: 自定义的Feign接口，调用商品微服务的所有的接口方法都在此进行定义
 * 注意：一个微服务最好只有一个接口因为在spring boot 2.1之后 定义多个同名FeignClient的name会报错
 * fallback:指定回退的类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-09 22:11
 * @PackageName: com.prim.page.feign
 * @ClassName: ProductFeign.java
 **/
@FeignClient(name = "lagou-service-product",fallback = ProductFeignFallback.class)
public interface ProductFeign {

    /**
     * 通过商品id查询商品的对象
     * @param id
     * @return
     */
    //GetMapping url 要是全路径
    @GetMapping("/product/query/{id}")
     Products query(@PathVariable Integer id);

    /**
     * 获取端口号的信息
     * @return
     */
    @GetMapping("/server/port")
    String getPort();
}
