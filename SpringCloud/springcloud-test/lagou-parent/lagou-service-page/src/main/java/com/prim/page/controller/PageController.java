package com.prim.page.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.prim.common.pojo.Products;
import com.prim.page.feign.ProductFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @program: springclouddemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-03 22:13
 * @PackageName: com.prim.page.controller
 * @ClassName: PageController.java
 **/
@RestController
@RequestMapping("/page")
public class PageController {

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private ProductFeign productFeign;

    @GetMapping("/getProduct/{id}")
    public Products getProduct(@PathVariable Integer id) {
        //获得lagou-server-product在服务注册中心注册的服务列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-product");
//        //商品服务并没有集群，获得商品服务列表中的第一个即可
//        ServiceInstance serviceInstance = instances.get(0);
//        // 获取自定义的元数据
//        Map<String, String> metadata = serviceInstance.getMetadata();
//        //获得商品微服务的主机地址
//        String host = serviceInstance.getHost();
//        //获得商品微服务的端口号
//        int port = serviceInstance.getPort();
//        String url = "http://lagou-service-product/product/query/" + id;
        //发送HTTP请求给商品服务。将id传递过去获取锁对应Products
//        String url = "http://localhost:8001/product/query/" + id;
        //HTTP的远程调用
//        Products products = restTemplate.getForObject(url, Products.class);

        Products products = productFeign.query(id);
        return products;
    }

    @GetMapping("/getPort")
    public String getServerPort() {
//        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-product");
//        ServiceInstance serviceInstance = instances.get(0);
//        String host = serviceInstance.getHost();
//        int port = serviceInstance.getPort();
        //直接写微服务的名称即可 不用再写上面的写法 直接由Ribbon走负载均衡算法
//        String url = "http://lagou-service-product/server/port";
//        String result = restTemplate.getForObject(url, String.class);

        String result = productFeign.getPort();
        return result;
    }

    /**
     * 模拟服务超时熔断处理
     * 针对熔断处理，Hystrix默认维护一个线程池，默认大小为10
     *
     * @return
     */
    @HystrixCommand(
            threadPoolKey = "getPort2", //每个方法维护一个线程池，如果不写该配置 默认所有的请求共同维护一个线程池，实际开发中每个方法维护一个线程池
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),//并发线程数
                    @HystrixProperty(name = "maxQueueSize", value = "20"), //默认线程队列值是-1 默认不开启。
            }, // 每一个属性对应的都是HystrixProperty
            //超时时间的设置
            commandProperties = {
                    //设置请求的超时时间，一旦请求超时按照超时处理,默认超时时间1000ms
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            }
    )
    @GetMapping("/getPort2")
    public String getServerPort2() {
        //直接写微服务的名称即可 不用再写上面的写法 直接由Ribbon走负载均衡算法
        String url = "http://lagou-service-product/server/port";
//        String result = restTemplate.getForObject(url, String.class);
        String result = productFeign.getPort();
        return result;
    }

    /**
     * 服务降级是在服务熔断之后的兜底操作
     */
    @HystrixCommand(
            //超时时间的设置
//            commandProperties = {
//                    //设置请求的超时时间，一旦请求超时按照超时处理,默认超时时间1000ms
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//                    // 统计窗口时间的设置
//                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "8000"),
//                    // 统计窗口内的最小请求数
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
//                    // 统计窗口内的错误请求阈值的设置 50%
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//                    // 自我修复的活动窗口时间
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")
//            },
            //设置回退的方法
            fallbackMethod = "getProductServerPortFallback"
    )
    @GetMapping("/getPort3")
    public String getServerPort3() {
        //直接写微服务的名称即可 不用再写上面的写法 直接由Ribbon走负载均衡算法
        String url = "http://lagou-service-product/server/port";
//        String result = restTemplate.getForObject(url, String.class);
        String result = productFeign.getPort();
        return result;
    }

    /**
     * 定义回退方法，当请求发生熔断后执行，补救措施
     * 1. 方法的形参和原方法保持一致
     * 2. 方法的返回值与原方法保持一致
     */
    public String getProductServerPortFallback() {
        return "-1";
    }
}
