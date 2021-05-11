package com.nanny.controller.admin;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanny.biz.admin.BusinessBiz;
import com.nanny.biz.login.LoginBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;

import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;

/**
 * 总后台商户管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @change wph
 * @version 0.1
 */
@Controller
public class AdminBusinessController {

	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private BusinessBiz businessBiz;
	
	@Resource(name="loginBiz")
	private LoginBiz g_LoginBiz;
	
	/**
	 * 前往总后台商户管理页面
	 * @param pageIndex
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/business.html")
	public String businessList(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer pageSize,HttpServletRequest request, HttpServletResponse response) {
		PageUtil pageUtil = new PageUtil();
		
		if(pageIndex==null){
			
			pageIndex = 1;
		}
		
		if(pageSize==null){
			
			pageSize = 10;
		}
		
		pageUtil.setPageIndex(pageIndex);
		pageUtil.setPageSize(pageSize);
			QueryParam queryParam = new QueryParam();
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();
			
			String shopname=request.getParameter("many");
			String situation=request.getParameter("state");
			
			
			//paramMap.put("status", 0);
			//typeMap.put("status", "eq");
			
			if(!"".equals(shopname) && shopname !=null){
				paramMap.put("shopName", "%"+shopname+"%");
				typeMap.put("shopName", "like");
			}
			if(!"".equals(situation) && situation !=null&&!"-1".equals(situation)){
				paramMap.put("situation", Integer.valueOf(situation));
				typeMap.put("situation", "eq");
			}
			
			orderMap.put("id", "desc");
			
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);
			
		JSONArray busin=businessBiz.doBusinessList(shopname, situation, pageUtil); //商户列表
		busin=getShopOrderCount(busin);
		
		
		PageUtil page = sshUtilBiz.getPageCount(BaseShop.class, queryParam, pageUtil);
		// 设置分页url
		page.setUrl("admin/business.html?");
		
		
		request.setAttribute("page", page);
		request.setAttribute("busin", busin);
		
		request.setAttribute("shopname", request.getParameter("many"));
		request.setAttribute("situation", request.getParameter("state"));
		
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/businessList";
	}
	
	/**
	 * 店铺开关
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updbusi.html")
	public void updateShopst(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String shopid=request.getParameter("shopid");
		   BaseShop shop=(BaseShop) sshUtilBiz.getObjectById(BaseShop.class, Long.valueOf(shopid));
		   
		   BaseUsers user=(BaseUsers) sshUtilBiz.getObjectById(BaseUsers.class, shop.getUserId());
		  
		   if(shop.getSituation()==0){  
			   shop.setSituation(1);
			   user.setBalance(user.getBalance()+shop.getBalance());
			   shop.setBalance(0D);
		   }else{
			   shop.setSituation(0);
			   shop.setBalance(user.getBalance());
			   user.setBalance(0D);
		   }
		   
		  
		    String msgg= businessBiz.updateBusiStatus(shop, user);
		   
		   	JSONObject msg=new JSONObject();
		   	msg.element("msg",msgg);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());
	}
	
	
	
	/**
	 * 更新店铺资料
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updshopdata")
	public void updateShop(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String shopname=request.getParameter("shopname");
		String shopcon=request.getParameter("shopcon");
		String shopmemo=request.getParameter("shopmemo");
		String shopicon=request.getParameter("shopicon");
		String shopid=request.getParameter("shopid");
		
		shopmemo  =  shopmemo.replace("，", ",");
			 
		String magg= businessBiz.updateShop(shopname, shopcon, shopicon, shopmemo, Integer.valueOf(shopid));
		
		JSONObject msg=new JSONObject();
			msg.element("msg",magg);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
	/**
	 * 更新商户状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updatesubsidysta")
	public void updateSubsidySta(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//0.获得传入参数
		int sta=Integer.valueOf(request.getParameter("sta"));
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		
		//1.更新商户状态
		businessBiz.updateShopSubSta(shopID, sta);
		
		//2.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 更新商户积分兑换状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updatetranbysta")
	public void updateTransferSta(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//0.获得传入参数
		int sta=Integer.valueOf(request.getParameter("sta"));
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		
		//1.更新商户状态
		businessBiz.updateShopTransfer(shopID, sta);
		
		//2.返回消息
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
	
	
	/**
	 * 更改商户优惠券状态
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/updateIsVoucher")
	public void updateIsVoucher(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		
		int isVouchers=Integer.valueOf(request.getParameter("isVouchers"));
		Long shopID=Long.valueOf(request.getParameter("shopID"));
		businessBiz.updateIsVoucher(shopID,isVouchers);
	
		JSONObject msg=new JSONObject();
		msg.element("msg", "设置成功");
		Dto.printMsg(response, msg.toString());
	}
	
	/**
	 * 总后台管理用户账户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("admin/toshopplat")
	public void adminManaShop(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		//0.获得传入参数
		String tel=request.getParameter("tel");
		String shopID=request.getParameter("shopId");
		JSONObject adminMsg=(JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		//1.读取管理对象的用户信息
		JSONObject shopMsg;
		if(shopID==null)
			shopMsg=g_LoginBiz.getLoginUserMsg(tel);
		else
			shopMsg=g_LoginBiz.getLoginUserMsgByShopID(Long.valueOf(shopID));
		session.setAttribute(Dto.LOGIN_USER, shopMsg);
		session.setAttribute(Dto.LOGIN_ADMIN_TMP, adminMsg);
		
		//2.重定向
		response.sendRedirect("/nanny/shop/shopIndex.html");
		return;
	}
	
	/**
	 * 返回总后台管理页面
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("admin/backtoadminplat")
	public void backToAdminPlat(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session=request.getSession();
		//0.获得传入参数
		JSONObject adminMsg=(JSONObject) session.getAttribute(Dto.LOGIN_ADMIN_TMP);
		session.setAttribute(Dto.LOGIN_USER, adminMsg);
		session.removeAttribute(Dto.LOGIN_ADMIN_TMP);
		
		//1.重定向
		response.sendRedirect("/nanny/admin/business.html");
		return;
	}
	
/*******************************************************************辅助方法***************************************************************/	
	
	/**
	 * 获得商户营业额统计
	 * @param shopList
	 * @return
	 */
	private JSONArray getShopOrderCount(JSONArray shopList)
	{
		for(int i=0;i<shopList.size();i++)
		{
			JSONObject tmpObj=shopList.getJSONObject(i);
			//查询店铺营业额
			JSONObject turnover= businessBiz.getShopTurnover(tmpObj.getLong("id"));
			if(("null").equals(turnover.getString("total")))
				tmpObj.element("turnover", 0);
			else
				tmpObj.element("turnover", turnover.getDouble("total"));
			tmpObj.element("orderCount", turnover.getInt("count"));
		}
		return shopList;
	}
}
