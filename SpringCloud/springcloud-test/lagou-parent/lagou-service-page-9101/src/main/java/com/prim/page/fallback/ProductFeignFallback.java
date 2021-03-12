package com.prim.page.fallback;

import com.prim.common.pojo.Products;
import com.prim.page.feign.ProductFeign;
import org.springframework.stereotype.Component;

/**
 * @program: springclouddemo
 * @Description: 熔断器触发之后的回调逻辑
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-09 22:36
 * @PackageName: com.prim.page.fallback
 * @ClassName: ProductFeignFallback.java
 **/
@Component
public class ProductFeignFallback implements ProductFeign {
    @Override
    public Products query(Integer id) {
        return null;
    }

    @Override
    public String getPort() {
        return "这是getPort的兜底数据";
    }
}
