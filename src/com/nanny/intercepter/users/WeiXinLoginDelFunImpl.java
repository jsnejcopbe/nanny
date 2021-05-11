package com.nanny.intercepter.users;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.weixin.Doweixin;


/**
 * 微信自动登录拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("weiXinLoginDelFun")
public class WeiXinLoginDelFunImpl implements CommonDelFun {

	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {

	}

	public boolean blReturnDelFu(HttpServletRequest request,HttpServletResponse response, Object handler) {
		HttpSession session=request.getSession();
		
		//0.判断是否为微信浏览器
		try{
			String ua = request.getHeader("user-agent").toLowerCase();
			if(ua.indexOf("micromessenger") > 0){
				//1.当为微信浏览器时，检查session和request中的参数
				Object userMsg=session.getAttribute(Dto.LOGIN_USER);//用户信息
				Object openId=session.getAttribute(Dto.WEIXIN_USER_OPENID);//用户openID
				String code=request.getParameter("code");//回调时微信以get方式回传code
				
				if(userMsg==null && openId==null)
				{
					//2.当用户信息和openid不存在时，进入微信授权流程
					
						return weixinOauth(code, response, request);
					
				}else
					return true;
			}else
				return true;
		}catch(Exception e){
			String name=request.getRequestURI();
			Dto.writeLog("post apply occur no user-agent error,post add is"+name);
			e.printStackTrace();
			return true;
		}
	}


	/**
	 * 微信授权操作
	 * @param code
	 * @throws IOException 
	 */
	private boolean weixinOauth(String code,HttpServletResponse response,HttpServletRequest request) throws IOException
	{
		System.out.println("weinxinCode:"+code+"\n");
		
		HttpSession session=request.getSession();
		if(code==null)
		{
			String url=Doweixin.getCodeURL(request.getRequestURL().toString());
			if(url.indexOf(".html")==-1)
				url+="home.html";
//			String url=Doweixin.getCodeURL("http://www.zsbaomu.com:8080/nanny/home.html");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head>" +
					"</script><script type='text/javascript'>" +
					"(function(){window.location.href ='"+ url +"'}());" +
					"</script></head><body></body></html>");
			out.flush();
			out.close();
			return false;
		}else{
			System.out.println("....into method to get the token and openID....\n");
			
			JSONObject weixinData=Doweixin.getAccessToken(code);//获得access_token、openID
			
			System.out.println("weixinData:"+weixinData+"\n");
			
			JSONObject userInfo=Doweixin.getUserInfo(weixinData.getString("access_token"), weixinData.getString("openid"));//获得微信下的用户信息
			session.setAttribute(Dto.WEIXIN_USER_OPENID, weixinData.getString("openid"));
			session.setAttribute(Dto.USER_WEIXIN_INFO, userInfo);
			
			System.out.println("....end the method.......\n");
			System.out.println("userInfo:"+userInfo+"\n");
			return true;
		}
	}
}
