package com.kfit.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import scala.annotation.meta.param;

public class StatelessAccessControlFilter extends AccessControlFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//1、客户端生成的消息摘要；
		String clientDigest = request.getParameter("digest");
		//2、客户端传入的用户身份；
		String username = request.getParameter("username");
		//3、客户端请求的参数列表；
		Map<String,String[]> params = new HashMap<String,String[]>(request.getParameterMap());
		params.remove("digest");//此参数不参与签名.
		//4、生成无状态Token
		StatelessAuthenticationToken token = new StatelessAuthenticationToken(username, params, clientDigest);
		
		try {
			//5、委托给Realm进行登录
			super.getSubject(request, response).login(token);
		} catch (Exception e) {
			//6. 登录失败. ---返回用户 login error.
			HttpServletResponse httpServletResponse = (HttpServletResponse)response;
			//返回401编码.
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpServletResponse.getWriter().write("login error");
			return false;//直接返回请求者.
		}
		return true;//登录成功.
	}

	
}
