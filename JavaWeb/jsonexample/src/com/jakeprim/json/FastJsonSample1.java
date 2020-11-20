package com.jakeprim.json;

import java.util.Calendar;

import com.alibaba.fastjson.JSON;

public class FastJsonSample1 {

	public static void main(String[] args) {
		Employee employee = new Employee();
		employee.setEmpno(1001);
		employee.setDname("技术部");
		employee.setEname("JakePrim");
		employee.setJob("软件工程师");
		employee.setSalary(17000.0);
		Calendar calendar = Calendar.getInstance();
		calendar.set(2020, 1, 1, 12, 12);
		employee.setDate(calendar.getTime());
		
		//将对象转换为Json字符串
		String jsonString = JSON.toJSONString(employee);
		System.out.println(jsonString);
		
		//将json字符串转换为对象
		Employee employee2 = JSON.parseObject(jsonString,Employee.class);
		System.out.println(employee2.toString());
	}
}
