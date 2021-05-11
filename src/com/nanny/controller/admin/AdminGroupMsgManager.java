package com.nanny.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhuoan.util.TimeUtil;

/**
 * 系统群发消息管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class AdminGroupMsgManager {
	
	
	/**
	 * 前往群发消息管理界面
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/groupmsg")
	public ModelAndView toGroupMsgManager(HttpServletResponse response,HttpServletRequest request)
	{
		//0.获得传入参数
		String shopID=request.getParameter("shopID");
		String time=request.getParameter("time");
		String page=request.getParameter("page");
		
		//1.组织查询参数
		JSONObject param=new JSONObject();
		if(shopID!=null && !("").equals(shopID))
			param.element("shopID", shopID);
		if(time!=null && !("").equals(time)){
			param.element("starTime", time+" 00:00:00");
			param.element("endTime", TimeUtil.addDaysBaseOnNowTime(param.getString("starTime"), 1, "yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}
}
