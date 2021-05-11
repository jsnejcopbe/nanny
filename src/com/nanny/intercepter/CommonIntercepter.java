package com.nanny.intercepter;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;


/**
 * 通用拦截器
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public class CommonIntercepter implements HandlerInterceptor {
	//加载工厂方法
	@Resource(name="delFunFactory")
	private DelFunFactory g_FunFactory;
	
	/**
	 * 资源清理
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception arg3)
			throws Exception {}

	/**
	 * 跳转前拦截
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mav) throws Exception {
		//订单数量计算
		g_FunFactory.getFunction("orderCount").noReturnDelFun(request, response, handler, mav);
		
		//获得拦截方法名
		String sMethodName="";
		if(mav!=null)
		{
			Map<String,Object> param=mav.getModel();
			sMethodName=(String) param.get("methodName");
		}else
			sMethodName=request.getParameter("methodName");
		CommonDelFun fnDelFun=g_FunFactory.getFunction(sMethodName);
		if(fnDelFun==null)
			return;
		else
			fnDelFun.noReturnDelFun(request, response, handler, mav);
	}

    
	/**
	 * 业务处理前置拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//通用拦截
		g_FunFactory.getFunction("weixinlogin").blReturnDelFu(request, response, handler);//微信拦截
		if(g_FunFactory.getFunction("common").blReturnDelFu(request, response, handler)){
			//获得拦截方法名
			String name=request.getRequestURI();
//			if(name.indexOf("updShopcar")!=-1 || name.indexOf("Payment")!=-1){
//				if(!g_FunFactory.getFunction("checkRep").blReturnDelFu(request, response, handler))
//					return false;
//			}
			
			
			MethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
			String sMethodName=methodNameResolver.getHandlerMethodName(request);
			CommonDelFun fnDelFun=null;
			if(name!=null)
				fnDelFun=g_FunFactory.getFunction(sMethodName);
			boolean value=true;
			if(fnDelFun!=null)	
				value=fnDelFun.blReturnDelFu(request, response, handler);
			return value;
		}else
			return false;
	}
}
