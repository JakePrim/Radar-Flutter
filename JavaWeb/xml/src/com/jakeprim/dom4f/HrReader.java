package com.jakeprim.dom4f;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HrReader {

	public void reader() {
		String file = "/Users/prim/jakeprim/work/java-workspace/xml/src/hr.xml";
		//读取XML的核心类,用于将XML解析后以树的形式保存在内存中
		SAXReader sReader = new SAXReader();
		try {
		 Document document = sReader.read(file);
		 //获取XML的根节点
		 Element rootElement = document.getRootElement();
		 List<Element> emploteesList = rootElement.elements("employee");
		 for (Element element : emploteesList) {
			 //employee 节点下的标签
			Element name = element.element("name");
			String empNameString = name.getText();//获取标签文本
			System.out.println(empNameString);
			//可以直接获取子标签的文本
			System.out.println(element.elementText("age"));
			System.out.println(element.elementText("salary"));
			Element pElement = element.element("department");
			System.out.println(pElement.elementText("dname"));
			System.out.println(pElement.elementText("address"));
			//属性如何提取
			Attribute attribute =  element.attribute("no");
			System.out.println(attribute.getText());
		}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HrReader hrReader = new HrReader();
		hrReader.reader();
	}
}
