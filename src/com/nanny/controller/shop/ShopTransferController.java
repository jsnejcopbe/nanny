package com.nanny.controller.shop;


import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.global.bank.BankBiz;
import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.dto.Dto;
import com.zhuoan.util.MathDelUtil;

/**
 * 商户提现控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class ShopTransferController {
	
	//载入资源
	@Resource
	private ShopAccountBiz g_AccBiz;
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_BaseMsgBiz;
	@Resource
	private BankBiz g_BankBiz;

	/**
	 * 前往商户提现页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("shop/transfer")
	public ModelAndView toShopTransfer(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得session中的信息
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		//1.获得商户当前余额
		Double totalBalance=g_BaseMsgBiz.getShopBalanceByShopID(userMsg.getLong("shopID"));
		//2.获得商户当前的不可用余额
		Double forbidBalance=g_AccBiz.getShopForbidCash(userMsg.getLong("shopID"));
		//3.获得商户绑定的银行账户
		JSONArray countList=g_BankBiz.bankinfo(userMsg.getInt("userID"));
		
		//返回信息
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/shopTransApp");
		mav.addObject("totalBalance", totalBalance);
		mav.addObject("forbidBalance", forbidBalance);
		if((totalBalance-forbidBalance)>0)
			mav.addObject("canUserBalance", MathDelUtil.halfUp(totalBalance-forbidBalance));
		else
			mav.addObject("canUserBalance", 0);
		mav.addObject("userID", userMsg.getLong("userID"));
		if(countList.size()>0){
			JSONObject acccount=countList.getJSONObject(0);
			String code=acccount.getString("account");
			mav.addObject("acccount", countList.getJSONObject(0));
			mav.addObject("splitAcc", code.substring(code.length()-4, code.length()));
		}
		return mav;
	}
	
	/**
	 * 获得账户信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("shop/balmsgajax")
	public void getBalanceMsg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得session中的信息
		HttpSession session=request.getSession();
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		//1.获得商户当前余额
		Double totalBalance=g_BaseMsgBiz.getShopBalanceByShopID(userMsg.getLong("shopID"));
		//2.获得商户当前的不可用余额
		Double forbidBalance=g_AccBiz.getShopForbidCash(userMsg.getLong("shopID"));
		//3.获得商户绑定的银行账户
		JSONArray countList=g_BankBiz.bankinfo(userMsg.getInt("userID"));
		
		JSONObject msg=new JSONObject();
		msg.element("totalBalance", totalBalance);
		msg.element("forbidBalance", forbidBalance);
		if((totalBalance-forbidBalance)>0)
			msg.element("canUserBalance", MathDelUtil.halfUp(totalBalance-forbidBalance));
		else
			msg.element("canUserBalance", 0);
		
		if(countList.size()>0){
			JSONObject acccount=countList.getJSONObject(0);
			String code=acccount.getString("account");
			msg.element("acccount", countList.getJSONObject(0));
			msg.element("splitAcc", code.substring(code.length()-4, code.length()));
		}
		
		Dto.printMsg(response, msg.toString());
	}
}
