package com.persistence.config;

import com.persistence.pojo.Configuration;
import com.persistence.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 18:33
 * @PackageName: com.persistence.config
 * @ClassName: XMLMapperBuilder.java
 **/
public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        //select标签
        List<Element> selectNodes = rootElement.selectNodes("//select");
        for (Element selectNode : selectNodes) {
            String id = selectNode.attributeValue("id");
            String resultType = selectNode.attributeValue("resultType");
            String parameterType = selectNode.attributeValue("parameterType");
            //获取SQL语句
            String sql = selectNode.getText();
            String statementid = namespace + "." + id;
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            //存储数据到configuration
            configuration.getMapper().put(statementid, mappedStatement);
        }
    }
}
