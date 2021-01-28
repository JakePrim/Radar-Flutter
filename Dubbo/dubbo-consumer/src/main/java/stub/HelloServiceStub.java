package stub;

import org.springframework.util.StringUtils;
import service.HelloService;

/**
 * HelloService 的本地存根
 * 本地存根必须以构造方法的形式注入
 */
public class HelloServiceStub implements HelloService {

    private HelloService helloService;

    //必须以构造方法的形式注入
    public HelloServiceStub(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String syHello(String name) {
        System.out.println("本地存根验证:" + name);
        //验证name 不能为空
        if (StringUtils.isEmpty(name)) {
            return "I'm Sorry";
        }
        return helloService.syHello(name);
    }

    @Override
    public String sayNo() {
        return helloService.sayNo();
    }
}
