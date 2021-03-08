package com.prim.page.controller;

import com.prim.common.pojo.Products;
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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getProduct/{id}")
    public Products getProduct(@PathVariable Integer id) {
        //获得lagou-server-product在服务注册中心注册的服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances("lagou-service-product");
        //商品服务并没有集群，获得商品服务列表中的第一个即可
        ServiceInstance serviceInstance = instances.get(0);
        // 获取自定义的元数据
        Map<String, String> metadata = serviceInstance.getMetadata();
        //获得商品微服务的主机地址
        String host = serviceInstance.getHost();
        //获得商品微服务的端口号
        int port = serviceInstance.getPort();
        String url = "http://" + host + ":" + port + "/product/query/" + id;
        System.out.println(url);

        //发送HTTP请求给商品服务。将id传递过去获取锁对应Products
//        String url = "http://localhost:8001/product/query/" + id;
        //HTTP的远程调用
        Products products = restTemplate.getForObject(url, Products.class);
        return products;
    }
}
