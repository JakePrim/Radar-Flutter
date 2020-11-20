package com.jakeprim.dom4f;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class HrWriter {
	public void writer() {
		String file = "/Users/prim/jakeprim/work/java-workspace/xml/src/hr.xml";
		//读取XML的核心类,用于将XML解析后以树的形式保存在内存中
		SAXReader sReader = new SAXReader();
		try {
		  Document document	= sReader.read(file);
		  Element root = document.getRootElement();
		  Element employee = root.addElement("employee");
		  employee.addAttribute("no", "1302");
		  employee.addElement("name").setText("王刚但");
		  employee.addElement("age").setText("18");
		  employee.addElement("salary").setText("1000");
		  Element department = employee.addElement("department");
		  department.addElement("dname").setText("技术部");
		  department.addElement("address").setText("1-1-103");
		  
		  Writer writer =new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
		  document.write(writer);
		  writer.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		HrWriter hrWriter = new HrWriter();
		hrWriter.writer();
	}
}
