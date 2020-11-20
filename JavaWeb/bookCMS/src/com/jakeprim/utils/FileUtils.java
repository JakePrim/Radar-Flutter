package com.jakeprim.utils;

import java.util.UUID;

public class FileUtils {

	public static String getUUIDName(String name) {
		int index = name.lastIndexOf(".");
		String exName = name.substring(index);//.jpg
		String uuidName = UUID.randomUUID().toString() + exName;
		return uuidName;
	}
	
	public static void main(String[] args) {
		System.out.println(FileUtils.getUUIDName("aaa.jpg"));
	}
}
