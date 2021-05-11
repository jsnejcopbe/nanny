package com.nanny.intercepter.users;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.login.LoginBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseSaleman;
import com.nanny.model.BaseSupplier;
import com.zhuoan.util.CookiesUtil;

public class SupplierInterceptor implements HandlerInterceptor{

			//加载资源
			@Resource(name="loginBiz")
			private  LoginBiz loginBiz;
			
			
			
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		RemLogin(arg0);//cookies登录
		
		String name=arg0.getRequestURI();
		if(name.indexOf("login")==-1)
		{
			
			JSONObject bs = (JSONObject) arg0.getSession().getAttribute(Dto.LOGIN_SUPPLIER);
			
			if(bs == null){
				String path = arg0.getContextPath();
				arg1.sendRedirect(path+"/supplier/login.html");
				return false;
			}
		}
		return true;
		
	}
        
	/**
	 * cookie自动登录
	 * @param request
	 * @return
	 */
	private void RemLogin(HttpServletRequest request ){
		if(request.getSession().getAttribute(Dto.LOGIN_SUPPLIER)==null)
		{
			String username=CookiesUtil.getCookie(Dto.SUPP_LOGIN_USERNAME, request);
			String password=CookiesUtil.getCookie(Dto.SUPP_LOGIN_PASSWORD, request);
			if(username!=null&&password!=null){
				//.查询用户
				JSONObject oUserObj=loginBiz.getLoginSupplierMsg(username,password);
				if(oUserObj!=null){
					request.getSession().setAttribute(Dto.LOGIN_SUPPLIER, oUserObj); //在session中填入信息
				}
			}
		}
	}
	
	
}
