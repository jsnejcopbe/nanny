package com.nanny.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

/**
 * 通用拦截器处理接口
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface CommonDelFun {
	
	/**
	 * 无返回值处理接口
	 * @param request
	 * @param response
	 * @param handler
	 * @param mav
	 */
	public void noReturnDelFun(HttpServletRequest request, HttpServletResponse response,
			                   Object handler, ModelAndView mav);
	
	/**
	 * 布尔返回值处理接口
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	public boolean blReturnDelFu(HttpServletRequest request, HttpServletResponse response,Object handler);
}
