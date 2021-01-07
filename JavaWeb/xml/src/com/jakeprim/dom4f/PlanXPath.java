package com.jakeprim.dom4f;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class PlanXPath {
	public void xpath(String exp) {
		String file = "/Users/prim/jakeprim/work/java-workspace/xml/src/planschema.xml";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file);
			List<Node> nodes = document.selectNodes(exp);
			for (Node node : nodes) {
				Element element = (Element) node;
				System.out.println(element.attributeValue("id"));
				System.out.println(element.elementText("course-name"));
				System.out.println(element.elementText("class-hour"));
				System.out.println(element.elementText("exam-form"));
				System.out.println("====================================");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PlanXPath xPath = new PlanXPath();
//		xPath.xpath("//course");//查询所有信息
//		xPath.xpath("/teaching-plan/course");//查询所有信息
//		xPath.xpath("//course[class-hour<50]");//查询课时小于50的课程信息
//		xPath.xpath("//course[course-name = '高等数学']");//查询课程名为高等数学的课程信息
//		xPath.xpath("//course[@id='1']");// 查询属性id为001的课程信息
		xPath.xpath("//course[position()<3]");//询前两条课程信息
	}
}
