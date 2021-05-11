package com.nanny.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.admin.AdminTransBiz;
import com.nanny.biz.cashrec.UserTransferBiz;
import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.dto.Dto;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.ShortMessageUtil;

/**
 * 总后台提现操作控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
@Controller
public class AdminTransferActController {

	//载入资源
	@Resource
	private ShopAccountBiz g_AccBiz;
	@Resource(name="adminTransBiz")
	private AdminTransBiz g_TransBiz;
	@Resource(name="userTransferBiz")
	private UserTransferBiz g_UserTranBiz;
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopMsgBiz;
	@Resource(name="userBaseMsgBiz")
	private UserBaseMsgBiz g_UserMsgBiz;
	
	/**
	 * 通过取现申请
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("admin/passtransfer")
	public void passTransfer(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		JSONObject msg=new JSONObject();
		//0.获得传入参数
		Long appID=Long.valueOf(request.getParameter("appID"));
		
		//1.获得提现申请记录
		JSONObject appMsg=g_UserTranBiz.getTransRecByID(appID);
		
		//2.组织查询数据
		JSONObject condition=new JSONObject();
		condition.element("appID", appID);
		condition.element("money", appMsg.getDouble("money"));
		if(("null").equals(appMsg.getString("shopID")))
			condition.element("userID", appMsg.getLong("userID"));
		else
			condition.element("shopID", appMsg.getLong("shopID"));
		
		//3.更新数据
		g_TransBiz.updateTablesForPass(condition);
		
		//4.发送短信
		ShortMessageUtil.sendMessage("【掌上保姆社区】您的提现申请已通过，请关注您的银行账户余额", appMsg.getString("userTel"));
		
		//5.返回消息
		msg.element("msg", "设置成功");

		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 拒绝取现
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/refusetransfer")
	public void refuseTransfer(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		Long appID=Long.valueOf(request.getParameter("appID"));
		
		//1.获得提现申请记录
		JSONObject appMsg=g_UserTranBiz.getTransRecByID(appID);
		
		//2.组织查询数据
		JSONObject condition=new JSONObject();
		condition.element("appID", appID);
		condition.element("money", appMsg.getDouble("money"));
		if(("null").equals(appMsg.getString("shopID")))
			condition.element("userID", appMsg.getLong("userID"));
		else
			condition.element("shopID", appMsg.getLong("shopID"));
		
		//3.更新数据
		g_TransBiz.updateTablesForRefuse(condition);
		
		ShortMessageUtil.sendMessage("【掌上保姆社区】您的提现申请已被拒绝 请注意您的账户余额变化", appMsg.getString("userTel"));
		
		//4.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 用户资金余额检查
	 * @return
	 */
	private boolean balanceCheck(JSONObject obj){
		//1.查询余额信息
		Double balance;
		Double factBalance;
		
		if(obj.containsKey("userID"))
			factBalance=balance=g_UserMsgBiz.getUserBalance(obj.getLong("userID"));
		else{
			factBalance=balance=g_ShopMsgBiz.getShopBalanceByShopID(obj.getLong("shopID"));
			Double forbidBalance=g_AccBiz.getShopForbidCash(obj.getLong("shopID"));
			balance=MathDelUtil.halfUp(balance-forbidBalance);
		}
		
		if(factBalance.doubleValue()<obj.getDouble("money"))
			return false;
		else
			return true;
	}
}
