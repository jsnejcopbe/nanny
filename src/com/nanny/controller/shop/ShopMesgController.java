package com.nanny.controller.shop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.nanny.biz.shop.ShopMesgBiz;
import com.nanny.biz.user.UserShopMsgBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserSiteMessage;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

/**
 * 商户站内信
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */


@Controller
public class ShopMesgController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private ShopMesgBiz shopMesgBiz;
	
	@Resource(name="UserShopMsgbizimpl")
	private UserShopMsgBiz usershopmsg;
	
	@RequestMapping("shop/shopMessage.html")
	public String shopMsglist(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		PageUtil pageUtil = new PageUtil();

		if (pageIndex == null) {

			pageIndex = 1;
		}

		if (pageSize == null) {

			pageSize = 50;
		}

		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
		
			QueryParam queryParam = new QueryParam();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();
			
			String state=request.getParameter("state");
			
			
			
			paramMap.put("otherSide", json.getLong("userID"));
			typeMap.put("otherSide", "eq");
			
			if(!"".equals(state) && state !=null&&!"-1".equals(state)){
				paramMap.put("isRead", Integer.valueOf(state));
				typeMap.put("isRead", "eq");
			}
		
			
			orderMap.put("id", "desc");
			
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);
			
			 JSONArray mesg= shopMesgBiz.getShopMsg(json.getInt("userID"),state, pageUtil);
		
		
		PageUtil page = sshUtilBiz.getPageCount(UserSiteMessage.class, queryParam, pageUtil);
		// 设置分页url
		page.setUrl("shop/shopMessage.html?");
		
		request.setAttribute("page", page);

		request.setAttribute("mesg", mesg);
		request.setAttribute("state",request.getParameter("state"));
		request.setAttribute("sid",json.getString("shopID"));  
		int url= (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/shopmsg";
		
		
		
	}
	
	/**
	 * 更改站内信状态
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("shop/updshopMessage.html")
	public void upd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String mgid=request.getParameter("id");
		
		UserSiteMessage smge =(UserSiteMessage) sshUtilBiz.getObjectById(UserSiteMessage.class, Long.valueOf(mgid));
		
		   if(smge.getIsRead()!=0){
			  
		   }else{
			   smge.setIsRead(1);
		   }
		
		sshUtilBiz.updateObject(smge);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "success");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
		
	}
	
	/**
	 * 店家回复
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/saveMessage.html")
	public void saveMessage(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String uid=request.getParameter("uid");
		String con=request.getParameter("con");
		
		UserSiteMessage message=new UserSiteMessage();
		
		message.setOtherSide(Long.valueOf(uid));
		message.setUserId(json.getLong("userID"));
		message.setMailCon(con);
		message.setIsRead(Dto.IS_MSG_READ);
		message.setCreateTime(DateUtils.getTimestamp());
		sshUtilBiz.saveObject(message);
		
		JSONObject msg=new JSONObject();
		msg.element("msg", "回复成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	/**
	 * 查看信息 
	 * @param request
	 * @param response
	 * @author LPC
	 * @throws IOException
	 */
	@RequestMapping("shop/shopmsgview-{id:\\d*}")
	public ModelAndView toshopmsgView(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int type = (Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);

		
		UserSiteMessage smge =(UserSiteMessage) sshUtilBiz.getObjectById(UserSiteMessage.class, Long.valueOf(id));
		   if(smge.getIsRead()!=0){
				  
		   }else{
			   smge.setIsRead(1);
		   }
		
		sshUtilBiz.updateObject(smge);
		//调用接口 的实现方法进行数据处理
		JSONArray obj =usershopmsg.getMsgByid(id);
		obj= TimeUtil.transTimestamp(obj, "createTime");
		ModelAndView lo = new ModelAndView(Dto.getPagePath(type) + "/shopmsgview");
		lo.addObject("list",obj.get(0));
		return lo;
	}


}
