package com.nanny.intercepter.users;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.login.LoginBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.CookiesUtil;
import com.zhuoan.util.MobileCheckUtil;

/**
 * 浏览器判断拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("userLoginStaCeckDelFun")
public class UserLoginStaCeckDelFunImpl implements CommonDelFun {
  
	//加载资源
		@Resource(name="loginBiz")
		private  LoginBiz loginBiz;
	
	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
	}

 
	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		
		HttpSession session =request.getSession();
		String head=request.getHeader("User-Agent");
		//0.浏览器判断		
//		if((head.toLowerCase().indexOf("firefox")<0 &&
//		   head.toUpperCase().indexOf("MSIE")<0 &&/		   head.toUpperCase().indexOf("CHROME")<0) || head.toLowerCase().indexOf("mobile")!=-1)
	
		try{
			if(MobileCheckUtil.check(head.toLowerCase()))
				session.setAttribute(Dto.PLAT_TYPE_NAME, 1);
			else
				session.setAttribute(Dto.PLAT_TYPE_NAME, 0);
		}catch(Exception e){
			String name=request.getRequestURI();
			Dto.writeLog("post apply occur no user-agent error,post add is"+name);
			session.setAttribute(Dto.PLAT_TYPE_NAME, 0);
			e.printStackTrace();
		}
		
		//1.自动登录列表
		RemLogin(request);//cookies登录
		WeixinAutoLogin(request);//openId登录
		
		//2.登录检查
		String name=request.getRequestURI();
		try{
			if(session.getAttribute(Dto.LOGIN_USER)==null && 
			  (name.indexOf("/pay/")!=-1 ||name.indexOf("/users/")!=-1 || name.indexOf("/shop/")!=-1 || name.indexOf("/admin/")!=-1) && 
			   name.indexOf("exp")==-1 && name.indexOf("/add.html")==-1 && name.indexOf("addprotocar")==-1)
			{
				
				if(name.indexOf("json")!=-1)
				{
					JSONObject msg=new JSONObject();
					msg.element("msg", "身份认证已过期，请重新登录");
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/plain");
					response.getWriter().write(msg.toString());
				}else
					response.sendRedirect("/nanny/login.html");
				return false;
			}else{
				//拿不到session
				/*JSONObject jsonSession= (JSONObject) session.getAttribute(Dto.LOGIN_USER);
				if(jsonSession!=null){
					 String token=MathDelUtil.getRandomCode(10);
					loginBiz.updateUserToken(token, jsonSession.getLong("userID"));
					CookiesUtil.addCookie(Dto.USER_TOKEN, token, response);
				}*/
				return true;
			}
		}catch(Exception e){e.printStackTrace();return false;}	
	}
        
	/**
	 * cookie自动登录
	 * @param request
	 * @return
	 */
	private void RemLogin(HttpServletRequest request ){
		if(request.getSession().getAttribute(Dto.LOGIN_USER)==null)
		{
			//String username=CookiesUtil.getCookie(Dto.REM_LOGIN_USERNAME, request);
			String token=CookiesUtil.getCookie(Dto.USER_TOKEN, request);
			if(token!=null){
				//.查询用户
				JSONObject oUserObj=loginBiz.getLoginUserMsg(token);
				if(oUserObj!=null)
					request.getSession().setAttribute(Dto.LOGIN_USER, oUserObj); //在session中填入信息
			}
		}
	}
	
	/**
	 * 微信自动登录
	 * @param request
	 */
	private void WeixinAutoLogin(HttpServletRequest request){
		HttpSession session=request.getSession();
		String ua = request.getHeader("user-agent").toLowerCase();
		if(ua.indexOf("micromessenger") > 0 && session.getAttribute(Dto.LOGIN_USER)==null){
			String openId=(String) session.getAttribute(Dto.WEIXIN_USER_OPENID);//用户openID
			if(openId!=null)
			{
				JSONObject userMsg=loginBiz.getLoginUserMsgByOpenId(openId);
				if(userMsg!=null)
					session.setAttribute(Dto.LOGIN_USER, userMsg); //在session中填入信息
			}
		}
	} 
}
