package controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.HelloService;

@Controller
public class HelloController {

    //    @Reference //远程去服务方将service注入进来
    @Autowired
    private HelloService helloService;

    @Autowired
    @Qualifier("helloService2")
    private HelloService helloService2;

    @GetMapping("hello")
    @ResponseBody
    public String syaHi(String name) {
        return helloService.syHello(name);
    }

    @GetMapping("no")
    @ResponseBody
    public String syaNo() {
        return helloService.sayNo();
    }

    @GetMapping("nov2")
    @ResponseBody
    public String syaNo2() {
        return helloService2.sayNo();
    }
}

