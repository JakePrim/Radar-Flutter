package service;

/**
 * 服务方接口声明，具体实现远程调用dubbo-server的service的实现类
 */
public interface HelloService {
    String syHello(String name);

    String sayNo();
}
