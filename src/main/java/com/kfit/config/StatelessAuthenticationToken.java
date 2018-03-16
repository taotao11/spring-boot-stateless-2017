package com.kfit.config;

import java.util.Map;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义 token
 */
public class StatelessAuthenticationToken implements AuthenticationToken{
	private static final long serialVersionUID = 1L;
	private String username;//用户身份 即用户名.
	private Map<String,?> parmas;//参数. -->存放参数的.
	private String clientDigest;//凭证 -->客户端传入的消息摘要.
	
	public StatelessAuthenticationToken() {
		
	}
	
	
	
	public StatelessAuthenticationToken(String usernmae, Map<String, ?> parmas, String clientDigest) {
		super();
		this.username = usernmae;
		this.parmas = parmas;
		this.clientDigest = clientDigest;
	}



	@Override
	public Object getPrincipal() {
		return username;
	}

	@Override
	public Object getCredentials() {
		return clientDigest;
	}


	



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Map<String, ?> getParmas() {
		return parmas;
	}



	public void setParmas(Map<String, ?> parmas) {
		this.parmas = parmas;
	}



	public String getClientDigest() {
		return clientDigest;
	}



	public void setClientDigest(String clientDigest) {
		this.clientDigest = clientDigest;
	}
	
	
	
}
