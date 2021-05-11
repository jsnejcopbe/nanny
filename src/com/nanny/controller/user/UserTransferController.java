package com.nanny.controller.user;

import java.io.IOException;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanny.biz.cashrec.UserTransferBiz;
import com.nanny.biz.global.bank.BankBiz;
import com.nanny.biz.user.UserBaseMsgBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserCashApply;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.util.ShortMessageUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.Configure;

/**
 * 用户提现控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class UserTransferController {

	//载入资源
	@Resource(name="userBaseMsgBiz")
	private UserBaseMsgBiz g_UserMsgBiz;
	@Resource(name="userTransferBiz")
	private UserTransferBiz g_TransBiz;
	@Resource
	private BankBiz g_BankBiz;
	
	/**
	 * 前往用户提现页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("users/transfer")
	public ModelAndView toUserTransfer(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		//0.获得session中的信息
		JSONObject userMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		//1.获得用户当前余额
		Double balance=g_UserMsgBiz.getUserBalance(userMsg.getLong("userID"));
		//2.获得用户的提现账户
		JSONArray countList=g_BankBiz.bankinfo(userMsg.getInt("userID"));
		
		//3.返回信息
		ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/userTransApp");
		mav.addObject("balance", balance);
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
	 * 添加新的提现申请记录
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("users/addtransferapp")
	public void addTransferApp(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		Long userID=Long.valueOf(request.getParameter("userID"));
		Long accountID=Long.valueOf(request.getParameter("accountID"));
		Double price=Double.valueOf(request.getParameter("price"));
		Object shopID=request.getAttribute("transferShopID");
		
		//1.组织保存数据
		UserCashApply bean=new UserCashApply();
		bean.setAccId(accountID);
		bean.setCreateTime(Timestamp.valueOf(TimeUtil.getNowDate()));
		bean.setMoney(price);
		bean.setStatus(Dto.TRANSFER_WAIT_CHECK);
		bean.setUserId(userID);
		
		Long transAppID=g_TransBiz.addNewTransferApp(bean,shopID==null?null:(Long)shopID);
		
		//2.发送短信
		JSONObject userMsg=g_UserMsgBiz.getUserMsgByUserID(userID);
		ShortMessageUtil.sendMessage("【掌上保姆社区】手机号码为"+userMsg.getString("tel")+"的用户申请提现，请及时处理", Configure.ANNOUNCE_ACCOUNT);
		//3.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "申请成功，请等待审核");
		msg.element("transAppID", transAppID);
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 查询用户提现记录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("users/transrec")
	public ModelAndView toTranAppRec(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		HttpSession session=request.getSession();
		JSONObject usesMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		int type=(Integer) session.getAttribute(Dto.PLAT_TYPE_NAME);
		
		//0.获得传入参数
		String page=request.getParameter("page");
		String start=request.getParameter("start");
		String end=request.getParameter("end");
		String jumpType=request.getParameter("type");
		
		//1.组织查询参数
		PageUtil pageUtil=new PageUtil();
		pageUtil.setPageIndex(page==null?1:Integer.valueOf(page));
		pageUtil.setPageSize(20);
		
		JSONObject condition=new JSONObject();
		if(start!=null && !("").equals(start))
			condition.element("start", start);
		if(end!=null && !("").equals(end))
			condition.element("end", end);
		
		//2.查询数据
		JSONObject transData=g_TransBiz.getTransRec(usesMsg.getLong("userID"), pageUtil, condition);
		
		//3.页面跳转
		if(jumpType!=null)
		{
			Dto.printMsg(response, transData.toString());
			return null;
		}else{
			ModelAndView mav=new ModelAndView(Dto.getPagePath(type)+"/transAppList");
			mav.addObject("data", transData);
			mav.addObject("page", pageUtil.getPageIndex());
			mav.addObject("size", 20);
			mav.addObject("start", start);
			mav.addObject("end", end);
			mav.addObject("userID", usesMsg.getLong("userID"));
			return mav;
		}
	}
}
