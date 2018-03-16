package com.kfit.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(String param1,String param2){
		return "helo,andy";
	}
	
	
	@RequestMapping("/hello1")
	public String hello1(){
		//应该是会报错的：Session creation has been disabled for the current subject 会话管理不可用
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		System.out.println(session);
		return "helo,andy";
	}
}
