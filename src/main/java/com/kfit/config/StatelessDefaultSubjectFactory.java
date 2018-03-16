package com.kfit.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * shiro 无状态 第一步 继承DefaultWebSubjectFactory
 *
 * 关闭session创建
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory{
	
	/**
	 * 第一：SubjectContext在创建的时候，需要关闭session的创建，这个主要是由DefaultWebSubjectFactory的createSubject进行管理。
	 */
	@Override
	public Subject createSubject(SubjectContext context) {
		//不进行创建session了.
		context.setSessionCreationEnabled(false);
		return super.createSubject(context);
	}
	
}
