package com.kfit.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * 需要编写两个对象：
 * -----------------
 * 1、ShiroFilterFactoryBean;
 * 2、DefaultWebSercurityManager;
 * 
 * @author Angel -- 守护天使
 * @version v.0.1
 * @date 2017年10月27日
 */
@Configuration
public class ShiroConfiguration {
	
	/**
	 * Shiro filter.
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		
		//配置.
		factoryBean.getFilters().put("statelessAuthc",statelessAccessControlFilter());
		
		//拦截请求.
		Map<String,String> filterChainDefinitionMap =new LinkedHashMap<String,String>();
		filterChainDefinitionMap.put("/**","statelessAuthc");
		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return factoryBean;
	}

	/**
	 * 安全管理器.
	 */
	@Bean
	public DefaultWebSecurityManager securityManager(){

		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		
		//设置subjectFactory.
		//关闭session的类
		securityManager.setSubjectFactory(subjectFactory());
		securityManager.setSessionManager(sessionManager());
		securityManager.setRealm(stalessRealm());
		
		/*
		 * 第二： 需要禁用使用Sessions 作为存储策略的实现，这个主要由securityManager的subjectDao的sessionStorageEvaluator进行管理的。
		 */
		((DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
		return securityManager;
	}
	
	
	/**
	 * suject 工厂管理器.
	 * 关闭session
	 */
	@Bean
	public DefaultWebSubjectFactory subjectFactory(){
		StatelessDefaultSubjectFactory statelessDefaultSubjectFactory = new StatelessDefaultSubjectFactory();
		return statelessDefaultSubjectFactory;
	}
	
	
	/**
	 * session管理器.
	 * 第三：需要禁用掉会话调度器，这个主要由sessionManager进行管理。
	 */
	@Bean
	public DefaultSessionManager sessionManager(){
		DefaultSessionManager sessionManager = new DefaultSessionManager();
		//关闭
		sessionManager.setSessionValidationSchedulerEnabled(false);
		return sessionManager;
	}
	
	/**
	 * 注入自定义的realm.
	 * @return
	 */
	@Bean
	public StatelessAuthorizingRealm stalessRealm(){
		StatelessAuthorizingRealm statelessAuthorizingRealm = new StatelessAuthorizingRealm();
		return statelessAuthorizingRealm;
	}
	
	/**
	 * 注入访问控制器.
	 */
	@Bean 
	public StatelessAccessControlFilter statelessAccessControlFilter(){
		StatelessAccessControlFilter statelessAccessControlFilter = new StatelessAccessControlFilter();
		return statelessAccessControlFilter;
	}
	
}
