package com.example.ioc.utils;

import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.xml.internal.txw2.output.SaxSerializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private static Map<String, Object> iocMap = new HashMap<>();

    //程序启动时 初始化对象实例
    static {
        //1. 读取配置文件
        ClassLoader classLoader = BeanFactory.class.getClassLoader();
        //2. 加载配置文件
        InputStream stream = classLoader.getResourceAsStream("beans.xml");
        //3. 解析xml
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(stream);
            //4. 编写xpath表达式
            String xpath = "//bean";//获取bean标签
            //根据xpath获取到所有bean标签
            List<Element> list = document.selectNodes(xpath);
            //5. 遍历并反射对象实例
            for (Element element : list) {
                String id = element.attributeValue("id");
                String className = element.attributeValue("class");
                //使用反射生成实例对象
                Object o = Class.forName(className).newInstance();
                //保存到Map - IOC 容器
                iocMap.put(id, o);
            }
        } catch (DocumentException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static Object getBean(String beanId) {
        return iocMap.get(beanId);
    }
}
