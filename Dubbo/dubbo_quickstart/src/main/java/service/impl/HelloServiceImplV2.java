package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import service.HelloService;

@Service
public class HelloServiceImplV2 implements HelloService {
    @Override
    public String syHello(String name) {
        System.out.println("===============v2.0---hello调用1次==============");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "Hello," + name;
    }

    @Override
    public String sayNo() {
        System.out.println("===============v2.0----no调用1次==============");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return "no";
    }
}
