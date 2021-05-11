package com.nanny.intercepter.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.TimeUtil;

/**
 * 阻止表单重复提交
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("preventRepActDelFun")
public class PreventRepActDelFunImpl implements CommonDelFun {

	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
		
	}

	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		HttpSession session=request.getSession();
		
		//0.获得请求路径&当前时间
		String name=request.getRequestURI();
		String time=TimeUtil.getNowDate();
	
		//1.获得session中的相关信息
		JSONObject reqMsg=(JSONObject) session.getAttribute("userRequestAct");
		
		try{
			if(reqMsg==null || !name.equals(reqMsg.getString("reqUrl"))){
				Dto.writeLog("当前用户请求地址信息为："+name+"，时间为："+time+"IP地址为："+Dto.getLocalIp(request));
				session.setAttribute("userRequestAct", new JSONObject().element("reqUrl", name).element("time", time));
				return true;
			}
			else{
				String betweenTime=TimeUtil.getDaysBetweenTwoTime(reqMsg.getString("time"), time, (long) (1000 * 60));
				if(Integer.valueOf(betweenTime)>=1)
				{
					Dto.writeLog("当前用户请求地址信息为："+name+"，时间为："+time+"IP地址为："+Dto.getLocalIp(request));
					session.setAttribute("userRequestAct", reqMsg.element("time", time));
					return true;
				}else{
					if(name.indexOf("json")!=-1)
					{
						JSONObject msg=new JSONObject();
						msg.element("msg", "为了保证系统稳定，请求间隔被限制为1分钟");
						Dto.printMsg(response, msg.toString());
					}
					return false;
				}
			}
		}catch(Exception e){e.printStackTrace();return false;}
		
	}

}
