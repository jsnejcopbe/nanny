package com.nanny.intercepter.users;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.user.UsersBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;

/**
 * 用户添加购物车前 积分判断
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("userPointCheckDelFun")
public class UserPointCheckDelFunImpl implements CommonDelFun {

//	@Resource
//	private AdminReturnPointsBiz g_PointBiz;
	@Resource
	private UsersBiz g_UserBiz;
	
	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
	}

	public boolean blReturnDelFu(HttpServletRequest request,HttpServletResponse response, Object handler) {
		//0.获得用户session信息
		JSONObject msg=new JSONObject();
		HttpSession session =request.getSession();
		JSONObject data=JSONObject.fromObject(request.getParameter("dataObj"));
		
//		JSONObject pointSet=g_PointBiz.setReturnCash();
		
		//1.计算需要的积分
		try{
			if(session.getAttribute(Dto.LOGIN_USER)!=null)
			{
				JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
				int needPonit=data.getInt("point");
//				JSONArray proData=JSONArray.fromObject(request.getParameter("arrayData"));
//				for(int i=0;i<proData.size();i++)
//				{
//					if(proData.getJSONObject(i).containsKey("ise")){
//						Double price=proData.getJSONObject(i).getDouble("price");
//						
//						needPonit+=(price / pointSet.getDouble("cash"));
//					}
//				}
				
				if(needPonit>g_UserBiz.getUserPointByUserID(userMsg.getLong("userID")))
				{
					msg.element("msg", "您的积分不足");
					Dto.printMsg(response, msg.toString());
					return false;
				}else
					return true;
			}
			else{
				msg.element("msg", "身份信息过期 请刷新页面重试");
				Dto.printMsg(response, msg.toString());
				return false;
			}
		}catch(Exception e){e.printStackTrace();return false;}
	}

}
