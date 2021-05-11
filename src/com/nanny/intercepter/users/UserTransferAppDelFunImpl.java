package com.nanny.intercepter.users;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.dto.Dto;
import com.nanny.intercepter.CommonDelFun;
import com.zhuoan.util.MathDelUtil;

/**
 * 用户提现前置拦截
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Component("userTransferAppDelFun")
public class UserTransferAppDelFunImpl implements CommonDelFun {
	
	//载入资源
	@Resource
	private ShopAccountBiz g_AccBiz;
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopMsgBiz;
	@Resource(name="userBaseMsgBiz")
	private UserBaseMsgBiz g_UserMsgBiz;

	public void noReturnDelFun(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
	}

	public boolean blReturnDelFu(HttpServletRequest request,HttpServletResponse response, Object handler) {
		//0.获得session中的用户信息&传入参数
		HttpSession session=request.getSession();
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		Double money=Double.valueOf(request.getParameter("price"));
		
		//1.查询余额信息
		Double balance;
		Double factBalance;
		if(("null").equals(userMsg.getString("shopID")))
			factBalance=balance=g_UserMsgBiz.getUserBalance(userMsg.getLong("userID"));
		else{
			factBalance=balance=g_ShopMsgBiz.getShopBalanceByShopID(userMsg.getLong("shopID"));
			Double forbidBalance=g_AccBiz.getShopForbidCash(userMsg.getLong("shopID"));
			balance=MathDelUtil.halfUp(balance-forbidBalance);
		}
		
		//2.账目核对
		if(g_AccBiz.getAccountBalance(userMsg.getLong("userID")).doubleValue() == factBalance.doubleValue())
		{
			//3.余额判断
			if(balance>=money)
			{
				if(!("null").equals(userMsg.getString("shopID")))
					request.setAttribute("transferShopID", userMsg.getLong("shopID"));
				return true;
			}else{
				try{
					JSONObject msg=new JSONObject();
					msg.element("msg", "您的余额不足");
					Dto.printMsg(response, msg.toString());
					return false;
				}catch(Exception e){e.printStackTrace();return false;}
			}
		}else{
			try{
				JSONObject msg=new JSONObject();
				msg.element("msg", "您的账目存在异常，请联系管理员核对");
				Dto.printMsg(response, msg.toString());
				return false;
			}catch(Exception e){e.printStackTrace();return false;}
		}
	}

}
