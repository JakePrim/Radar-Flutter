package com.jakeprim.dom4f;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class PlanXml {

	Document document;

	Element root;

	String file;

	public PlanXml() {
		file = "/Users/prim/jakeprim/work/java-workspace/xml/src/planschema.xml";
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(file);
			root = document.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新一条信息
	 */
	public void planUpdate(String id,String name,String hour,String form) {
		Element course = root.addElement("course");
		course.addElement("course-name").setText(name);
		course.addElement("class-hour").setText(hour);
		course.addElement("exam-form").setText(form);
		course.addAttribute("id", id);
		Writer writer = null;
		try {
			writer = new  OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			document.write(writer);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 读取xml
	 */
	public void planReader() {
		List<Element> courses = root.elements("course");
		for (Element course : courses) {
			System.out.println(course.elementText("course-name"));
			System.out.println(course.elementText("class-hour"));
			System.out.println(course.elementText("exam-form"));
			System.out.println(course.attribute("id").getText());
		}
	}
	
	public static void main(String[] args) {
	 PlanXml planXml = new PlanXml();
	 planXml.planUpdate("4", "Java", "100", "上机考试");
	 planXml.planReader();
	}
}
