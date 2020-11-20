package org.prim.ioc.demo3;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author prim
 */
public class Man implements BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {
    public Man() {
        System.out.println("Man()");
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("第二步：设置属性");
        this.name = name;
    }

    public void init() {
        System.out.println("第七步：Man被初始化了");
    }

    public void beanclose() {
        System.out.println("第11步：Man被销毁了");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("第10步：执行spring销毁方法");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("第三步：设置 Bean的名称 也就是<bean> id的值 = " + s);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("第四步：了解工厂的信息 = " + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("第六步：属性设置后执行");
    }

    public void run(){
        System.out.println("第九步：执行自身业务方法");
    }
}
