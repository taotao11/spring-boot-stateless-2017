package com.kfit.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class StatelessAuthorizingRealm extends AuthorizingRealm{

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessAuthenticationToken;
	}
	
	/**
	 * 身份校验.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		StatelessAuthenticationToken statelessToken = (StatelessAuthenticationToken)token;
		String username = statelessToken.getUsername();
		//此key需要和客户端一致.
		String key = "andy123456";//进行硬编码了，实际需要从配置文件或者数据库进行获取.
		
		String serverDigest = HmacSHA256Utils.digest(key,statelessToken.getParmas());
		
		System.out.println("serverDigest:"+serverDigest+",clientDigest:"+statelessToken.getClientDigest());
		
		//进行客户端消息摘要和服务端消息摘要的匹配.
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				username,
				serverDigest,
				getName()
				);
		return authenticationInfo;
	}
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
}
