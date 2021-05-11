package com.nanny.intercepter.refund;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;

/**
 * 退款前置拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("shopRefundDelFun")
public class ShopRefundDelFunImpl implements CommonDelFun {

	//载入资源
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopMsgBiz;
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;
	
	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {

	}

	public boolean blReturnDelFu(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		HttpSession session=request.getSession();
		//0.获得传递参数
		Long orderID=Long.valueOf(request.getParameter("orderID"));
		Double cost=Double.valueOf(request.getParameter("cost"));
		JSONObject userMsg= (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		//1.获得账户余额
		Double balance=g_ShopMsgBiz.getShopBalanceByShopID(userMsg.getLong("shopID"));
		JSONObject orderMsg=g_OrderBiz.getOrderBaseByOrderID(orderID);
		
		//2.判断余额
		request.setAttribute("orderMsg", orderMsg);
		if(balance<cost && ("null").equals(orderMsg.getString("memo")))
		{
			JSONObject msg=new JSONObject();
			msg.element("msg", "您的余额不足");
			msg.element("error", "error");
			try{
				Dto.printMsg(response, msg.toString());
			}catch(Exception e){e.printStackTrace();}
			return false;
		}
		else
			return true;
	}

}
