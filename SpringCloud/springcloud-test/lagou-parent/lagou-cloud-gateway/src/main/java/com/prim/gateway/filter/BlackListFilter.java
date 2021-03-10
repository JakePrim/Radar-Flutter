package com.prim.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springcloud-test
 * @Description: 定义全局过滤器，会对所有路由生效
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-10 10:59
 * @PackageName: com.prim.gateway.filter
 * @ClassName: BlackListFilter.java
 **/
@Slf4j
@Component //扫描注入到IOC容器中
public class BlackListFilter implements GlobalFilter, Ordered {

    //模拟黑名单列表 实例可以去数据库或者Redis中查询
    private static List<String> blackList = new ArrayList<>();

    static {
        blackList.add("0:0:0:0:0:0:0:1");
        blackList.add("127.0.0.1");
    }

    /**
     * 过滤器的核心方法
     * @param exchange 封装了request和response对象的上下文
     * @param chain 网关过滤器链 包含全局过滤器和单路由过滤器
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("----------BlackListFilter-----------");
//        ServerHttpRequest request = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//
//        // 从request对象获取客户端IP地址
//        String clienIp = request.getRemoteAddress().getHostString();
//        if (blackList.contains(clienIp)){
//            //存在黑名单中拒绝访问 返回状态数据
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            log.info("=====>IP:"+clienIp+" 在黑名单中，拒绝访问！");
//            String data = "Request be denied!";
//            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
//            return response.writeWith(Mono.just(wrap));
//        }
        // 合法请求执行 后续的过滤器
        return chain.filter(exchange);
    }

    /**
     * 返回值表示当前过滤器的顺序 优先级，数值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
