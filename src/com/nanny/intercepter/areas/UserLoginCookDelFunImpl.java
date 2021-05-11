package com.nanny.intercepter.areas;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.user.UserViewShopRecBiz;
import com.nanny.biz.user.UsersBiz;
import com.nanny.controller.global.GlobalCommonExtend;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.CookiesUtil;


/**
 * COOK判断拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
@Component("userLoginCookDelFun")
public class UserLoginCookDelFunImpl extends GlobalCommonExtend implements CommonDelFun{

	@Resource
	private UsersBiz usersBiz;
	@Resource(name="userViewShopRecBiz")
	private UserViewShopRecBiz g_ViewRecBiz;
	
	@Override
	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
		
	}

	@Override
	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		//取SESSION
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		//当前访问地址
		StringBuffer url = request.getRequestURL();
		if (request.getQueryString() != null) {
			url.append('?');
			url.append(request.getQueryString());
		}
		
		//String url=request.getRequestURL().toString();
		session.setAttribute(Dto.ACCESS_ADDRESS, url.toString());
		
		System.out.println(url);
		
		Long addid=null;
		//参数获取
		if(request.getParameter("addid")!=null && !("null").equals(request.getParameter("addid")))
			addid=Long.valueOf(request.getParameter("addid"));
		
		//session获取
		if(addid==null && session.getAttribute(Dto.SESSION_USER_AREA)!=null)
			addid=(Long) session.getAttribute(Dto.SESSION_USER_AREA);
		
		//cookies获取
		if(addid==null){
			String areaID= CookiesUtil.getCookie(Dto.USER_AREA, request);
			if(areaID!=null&&!"".equals(areaID)){
				addid=Long.valueOf(areaID);
			}
		}
		
		//微信访问记录中获取
		if(addid==null){
			String openID=(String) session.getAttribute(Dto.WEIXIN_USER_OPENID);
			if(openID!=null)
			{
				JSONObject locale=g_ViewRecBiz.getViewRecByOpenID(openID);
				if(locale!=null && !("null").equals(locale.getString("memo")))
				{
					JSONObject msg=super.helpGetPostion(locale.getString("memo"), null);
					if(msg.containsKey("addID"))
						addid=msg.getLong("addID");
				}
			}
		}
		
		//如果上个判断无效,判断登录
		if(addid==null && session.getAttribute(Dto.LOGIN_USER)!=null){
			JSONArray address=usersBiz.doUseraddress(json.getInt("userID"));
			if(address.size()!=0){
				addid=address.getJSONObject(0).getLong("memo");
			}
		}
		try{
			if(addid!=null){
				session.setAttribute(Dto.SESSION_USER_AREA, addid);
				return true;
			}else{
				response.sendRedirect("/nanny/global_city.html");
				return false;
			}
		}catch (Exception e) {
			return false;
		}
	}
		
}
