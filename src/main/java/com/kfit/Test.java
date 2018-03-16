package com.kfit;

import java.util.HashMap;
import java.util.Map;

import com.kfit.config.HmacSHA256Utils;


public class Test {
	// 测试连接/hello?username=admin&&params1=love&&params2=girl&&digest=df7f1595bd5682638556072c8ccde5edadcd807a829373d21af38fb1bc707da7
	public static void main(String[] args) {
		//实际中，此代码是由客户端进行编写. 
		String key = "andy123456";
		Map<String,String> map = new HashMap<String,String>();
		map.put("username","admin");
		map.put("params1","love");
		map.put("params2","girl");
		System.out.println(HmacSHA256Utils.digest(key, map));
	}
}
