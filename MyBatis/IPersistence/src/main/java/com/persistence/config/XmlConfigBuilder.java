package com.persistence.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.persistence.io.Resources;
import com.persistence.pojo.Configuration;
import jdk.internal.util.xml.impl.Input;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 17:02
 * @PackageName: com.persistence.config
 * @ClassName: XmlConfigBuilder.java
 **/
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用dom4j解析配置，封装为Configuration对象
     *
     * @param inputStream
     * @return Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //根标签 <configuration>
        Element rootElement = document.getRootElement();
        //查找property标签
        List<Element> list = rootElement.selectNodes("//property");
        // 键值对存储
        Properties properties = new Properties();
        //遍历标签 获取标签的属性值
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }

        //数据库连接池 数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("url"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        //把数据源存入configuration
        configuration.setDataSource(comboPooledDataSource);

        //解析mapper.xml 拿到路径
        List<Element> mapperElement = rootElement.selectNodes("//mapper");
        for (Element element : mapperElement) {
            String resource = element.attributeValue("resource");
            System.out.println("resource:" + resource);
            //获取mapper.xml的输入流
            InputStream resourceAsSteam = Resources.getResourceAsSteam(resource);
            //解析mapper.xml
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parseMapper(resourceAsSteam);
        }
        return configuration;
    }
}
