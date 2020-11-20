package com.jakeprim.json;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class FastJsonSample2 {

	public static void main(String[] args) {
		List<Employee> employees = new ArrayList<Employee>();
		
		for (int i = 0; i < 10; i++) {
			Employee employee = new Employee();
			employee.setEmpno(1001);
			employee.setDname("技术部");
			employee.setEname("JakePrim");
			employee.setJob("软件工程师");
			employee.setSalary(17000.0);
			Calendar calendar = Calendar.getInstance();
			calendar.set(2020, 1, 1, 12, 12);
			employee.setDate(calendar.getTime());
			
			employees.add(employee);
		}
		
		
		//将数组对象转换为Json字符串
		String jsonString = JSON.toJSONString(employees);
		System.out.println(jsonString);
		
		//将json字符串转换为数组对象
		List<Employee> employees2 = JSON.parseArray(jsonString, Employee.class);
		System.out.println("employees2:"+employees2.size());
	}
}
