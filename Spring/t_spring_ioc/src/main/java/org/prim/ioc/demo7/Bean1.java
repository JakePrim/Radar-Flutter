package org.prim.ioc.demo7;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author prim
 */
@Component("bean1")
@Scope("prototype")
public class Bean1 {
    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy");
    }

    public void say() {
        System.out.println("say...");
    }
}
