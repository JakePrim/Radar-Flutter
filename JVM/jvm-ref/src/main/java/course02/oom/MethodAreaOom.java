package course02.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 方法区导致的内存溢出
 * 设置VM运行的参数
 * MetaspaceSize :
 * VM Args" -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
 */
public class MethodAreaOom {
    public static void main(String[] args) {
        while (true){
            //不断的生成class 使用cglib
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(TestObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return  methodProxy.invoke(o,objects);
                }
            });
            enhancer.create();
        }
    }
    public static class TestObject {
        private double a = 34.53;
        private  Integer b = 9999999;
    }
}


