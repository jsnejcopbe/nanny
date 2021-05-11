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
import com.zhuoan.util.CookiesUtil;

public class SalesmanInterceptor implements HandlerInterceptor{

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
		WeixinAutoLogin(arg0);//openId登录
		
		String name=arg0.getRequestURI();
		if(name.indexOf("login")==-1)
		{
			
			BaseSaleman bs = (BaseSaleman) arg0.getSession().getAttribute(Dto.LOGIN_SALEMAN);
			
			if(bs == null){
				String path = arg0.getContextPath();
				arg1.sendRedirect(path+"/salesman.html");
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
		if(request.getSession().getAttribute(Dto.LOGIN_SALEMAN)==null)
		{
			String username=CookiesUtil.getCookie(Dto.SALES_LOGIN_USERNAME, request);
			String password=CookiesUtil.getCookie(Dto.SALES_LOGIN_PASSWORD, request);
			if(username!=null&&password!=null){
				//.查询用户
				JSONObject oUserObj=loginBiz.getLoginSalesMsg(username,password);
				if(oUserObj!=null){
					BaseSaleman bs=new BaseSaleman();
					bs.setTel(oUserObj.getString("tel"));
					bs.setQq(oUserObj.getString("qq"));
					bs.setPassword(oUserObj.getString("password"));
					bs.setName(oUserObj.getString("name"));
					bs.setMemo(oUserObj.getString("memo"));
					bs.setMail(oUserObj.getString("mail"));
					bs.setGuid(oUserObj.getString("guid"));
					bs.setExtension(oUserObj.getInt("extension"));
					bs.setId(oUserObj.getLong("id"));
					request.getSession().setAttribute(Dto.LOGIN_SALEMAN, bs); //在session中填入信息
				}
			}
		}
	}
	
	/**
	 * 微信自动登录
	 * @param request
	 */
	private void WeixinAutoLogin(HttpServletRequest request){
		HttpSession session=request.getSession();
		String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
		if(ua.indexOf("micromessenger") > 0 && session.getAttribute(Dto.LOGIN_SALEMAN)==null){
			
			String openId=(String) session.getAttribute(Dto.WEIXIN_USER_OPENID);//用户openID
			System.out.println("openID="+openId);
			JSONObject userMsg=loginBiz.getLoginSalesMsgByOpenId(openId);
			System.out.println("openID  select to list="+userMsg);
			if(userMsg!=null){
				BaseSaleman bs=new BaseSaleman();
				bs.setTel(userMsg.getString("tel"));
				bs.setQq(userMsg.getString("qq"));
				bs.setPassword(userMsg.getString("password"));
				bs.setName(userMsg.getString("name"));
				bs.setMemo(userMsg.getString("memo"));
				bs.setMail(userMsg.getString("mail"));
				bs.setGuid(userMsg.getString("guid"));
				bs.setId(userMsg.getLong("id"));
				bs.setExtension(userMsg.getInt("extension"));
				session.setAttribute(Dto.LOGIN_SALEMAN, bs); //在session中填入信息
			}
		}
	} 
	
	
}
