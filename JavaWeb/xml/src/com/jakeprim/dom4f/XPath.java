package com.jakeprim.dom4f;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class XPath {

	public void xpath(String exp) {
		String file = "/Users/prim/jakeprim/work/java-workspace/xml/src/hr.xml";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file);
			//传入xpath表达式
			List<Node> nodes = document.selectNodes(exp);
			for (Node node : nodes) {
			   Element empElement = (Element) node;
			   System.out.println(empElement.attribute("no").getText());
			   System.out.println(empElement.elementText("name"));
			   System.out.println(empElement.elementText("age"));
			   System.out.println(empElement.elementText("salary"));
			   System.out.println("=================================");
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		XPath path = new XPath();
//		path.xpath("/hr/employee");// / 必须按照节点顺序走
//		path.xpath("//employee");// //不管在哪个位置,层级在哪 都能获取到
//		path.xpath("//employee[salary<4000]");// [] 筛选工资小于4000的员工
//		path.xpath("//employee[1]");// 筛选第一个
//		path.xpath("//employee[last()]");//筛选最后一个
//		path.xpath("//employee[name = '王刚但3']");
//		path.xpath("//employee[@no=1304]");//筛选属性
//		path.xpath("//employee[position()<6]");//按范围筛选
		path.xpath("//employee[3] | //employee[5]");//组合合并
	}
}
