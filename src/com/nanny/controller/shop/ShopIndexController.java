package com.nanny.controller.shop;

import java.io.IOException;
import java.text.ParseException;
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


import com.nanny.biz.global.statistics.StatisticsBiz;
import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.nanny.biz.shop.ShopBasis;
import com.nanny.dto.Dto;
import com.nanny.model.BaseOrders;
import com.nanny.model.ShopProduct;
import com.zhuoan.shh.biz.SSHUtilBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;

@Controller
public class ShopIndexController {
	@Resource
	private SSHUtilBiz sshUtilBiz;
	
	@Resource
	private  ShopBasis  ShopBasis;
	@Resource(name="shopBaseMsgBiz")
	private ShopBaseMsgBiz g_ShopMsgBiz;
	
	@Resource
	private StatisticsBiz sbl;
	
	/**
	 * 商户后台首页统计
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("shop/shopIndex")
	public String shopIndex(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		if(("null").equals(json.getString("shopID"))){
			response.sendRedirect("/nanny/"+Dto.LOGIN_USER_SEND);
			return null;
		}else{
			QueryParam queryParam = new QueryParam();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, String> typeMap = new HashMap<String, String>();
			Map<String, String> orderMap = new HashMap<String, String>();
			
			
			paramMap.put("shopId", json.getLong("shopID"));
			typeMap.put("shopId", "eq");
			
			queryParam.setParamMap(paramMap);
			queryParam.setTypeMap(typeMap);
			queryParam.setOrderMap(orderMap);
			
			
			String colName="id";
			//商铺商品总数
			int product= sshUtilBiz.getObjectCount(ShopProduct.class, colName, queryParam);
			
			
			QueryParam queryParam1 = new QueryParam();
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			Map<String, String> typeMap1 = new HashMap<String, String>();
			Map<String, String> orderMap1 = new HashMap<String, String>();
			
			
			paramMap1.put("recShopId", json.getLong("shopID"));
			typeMap1.put("recShopId", "eq");
			
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			queryParam1.setOrderMap(orderMap1);
			//商户余额
			Double balance=g_ShopMsgBiz.getShopBalanceByShopID(json.getLong("shopID"));
			//平台商户订单总数
			int order=sshUtilBiz.getObjectCount(BaseOrders.class, colName, queryParam1);
			
			
			paramMap1.put("status", 0);
			typeMap1.put("status", "eq");
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			
			//平台商户 待付款订单数
			int order0=sshUtilBiz.getObjectCount(BaseOrders.class, colName, queryParam1);
			
			paramMap1.put("status", 1);
			typeMap1.put("status", "eq");
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			
			//平台商户 待发货 订单数
			int order1=sshUtilBiz.getObjectCount(BaseOrders.class, colName, queryParam1);
			
			paramMap1.put("status",2);
			typeMap1.put("status", "eq");
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			//平台商户 退款 订单数
			int order4=sshUtilBiz.getObjectCount(BaseOrders.class, colName, queryParam1);
			
			paramMap1.put("status", 3);
			typeMap1.put("status", "ge");
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			//平台商户 已完成 订单数
			int order3=sshUtilBiz.getObjectCount(BaseOrders.class, colName, queryParam1);
			
			PageUtil pageUtil = new PageUtil();
			
			pageUtil.setPageIndex(1);
			pageUtil.setPageSize(5);
			paramMap1.clear();
			typeMap1.clear();
			paramMap1.put("recShopId", json.getLong("shopID"));
			typeMap1.put("recShopId", "eq");
			orderMap1.put("id", "desc");
			queryParam1.setParamMap(paramMap1);
			queryParam1.setTypeMap(typeMap1);
			queryParam1.setOrderMap(orderMap1);
			JSONArray   orderlist= JSONArray.fromObject(sshUtilBiz.getObjectList(BaseOrders.class, queryParam1, pageUtil));
			for(int i=0;i<orderlist.size();i++){
				JSONObject bean=orderlist.getJSONObject(i);
				bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd hh:mm:ss", "createTime");
			}
			
			//商铺每日下单数
			JSONArray orsum=  sbl.dayordersum(json.getInt("shopID"));
			
			
			request.setAttribute("product",product);
			request.setAttribute("order",order);
			request.setAttribute("order1",order1);
			request.setAttribute("order4",order4);
			request.setAttribute("order3",order3);
			request.setAttribute("order0",order0);
			request.setAttribute("shopBalance",balance);
			request.setAttribute("orderlist",orderlist);
			request.setAttribute("orsum",orsum);
			
			int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
			return Dto.getPagePath(url)+"/shopIndex";
		}
	}
	/**
	 * 商户关闭店铺
	 * @param request
	 * @param response
	 * @author LPC
	 * @throws 
	 * @return
	 */
	@RequestMapping("closeshop")
	  public void colseShop(HttpServletRequest request,HttpServletResponse response)throws IOException{
		  //获得参数
		  HttpSession session=request.getSession();
			JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
	        String shopID =object.getString("shopID");
	        //关闭店铺
	        JSONObject  closeresult =  ShopBasis.closeshop(shopID);
	        object.element("status", 1);
            request.getSession().setAttribute(Dto.LOGIN_USER, object);
	        //返回	        
	        JSONObject msg = new JSONObject();
			msg.element("msg","店铺关闭了" );
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());		        
	  }
	/**
	 * 商户打开店铺
	 * @param request
	 * @param response
	 * @author LPC
	 * @throws 
	 * @return
	 */
	@RequestMapping("openshop")
	  public void openShop(HttpServletRequest request,HttpServletResponse response)throws IOException{
		  //获得参数
		  HttpSession session=request.getSession();
			JSONObject object = (JSONObject) session.getAttribute(Dto.LOGIN_USER);
	        String shopID =object.getString("shopID");
	         
	        //关闭店铺
	        JSONObject  closeresult =  ShopBasis.openshop(shopID);
	        object.element("status", 0);
            request.getSession().setAttribute(Dto.LOGIN_USER, object);
	        //返回	        
	        JSONObject msg = new JSONObject();
			msg.element("msg","店铺打开了" );
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain");
			response.getWriter().println(msg.toString());		        
	  }
	
	@RequestMapping("shop/jump_shopdate.html")
	public String jump_shop(HttpServletRequest request,HttpServletResponse response) {
		
		 String date=TimeUtil.getNowDate("yyyy-MM-dd");
		 request.setAttribute("newdate",date);
		int url= (Integer) request.getSession().getAttribute(Dto.PLAT_TYPE_NAME);
		return Dto.getPagePath(url)+"/shopBusinessStatistics";
		
	}
	
	
	
	@RequestMapping("shop/DateSurve.html")
	public void Surve(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		HttpSession session =request.getSession();
		JSONObject json=  (JSONObject) session.getAttribute(Dto.LOGIN_USER);
		
		String date=request.getParameter("datetime");
		String op=request.getParameter("op");
		
		if("".equals(date)&&date==null){
			 date=TimeUtil.getNowDate("yyyy-MM-dd");
		}
		
		if(op!=null&&!"".equals(op)){
			if("0y".equals(op)){
			 date=TimeUtil.addTimeBaseOnNowTime(date+" 00:00:00",-12);
			}else if("1y".equals(op)){
				date=TimeUtil.addTimeBaseOnNowTime(date+" 00:00:00",12);
			}else if("0m".equals(op)){
				date=TimeUtil.addTimeBaseOnNowTime(date+" 00:00:00", -1);
			}else if("1m".equals(op)){
				date=TimeUtil.addTimeBaseOnNowTime(date+" 00:00:00",1);
			}else if("0d".equals(op)){
				date=TimeUtil.addDaysBaseOnNowTime(date+" 00:00:00", -1, "yyyy-MM-dd");
			}else if("1d".equals(op)){
				date=TimeUtil.addDaysBaseOnNowTime(date+" 00:00:00",1,"yyyy-MM-dd");
			}
		}
		
		
		
		
		QueryParam queryParam = new QueryParam();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap= new HashMap<String, String>();
		Map<String, String> orderMap= new HashMap<String, String>();
		
		
		paramMap.put("recShopId", json.getLong("shopID"));
		typeMap.put("recShopId", "eq");
			
		paramMap.put("status", 3);
		typeMap.put("status", "eq");
		Object[] param = {
				TimeUtil.StrTsfToDate(date, "yyyy-MM-dd"),
				TimeUtil.StrTsfToDate(date + " 23:59:59",
						"yyyy-MM-dd HH:mm:ss") };

		paramMap.put("createTime", param);
		typeMap.put("createTime", "bet");
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		queryParam.setOrderMap(orderMap);
		
		String colName="totalPrice";
		String colName1="id";
		//平台商户日订单数
		int dayor=sshUtilBiz.getObjectCount(BaseOrders.class, colName1, queryParam);
		//平台商户日营业额
		double daymo=  sshUtilBiz.getSum(BaseOrders.class, colName, queryParam);
		paramMap.clear();
		typeMap.clear();
		paramMap.put("recShopId", json.getLong("shopID"));
		typeMap.put("recShopId", "eq");
			
		paramMap.put("status", 3);
		typeMap.put("status", "eq");
		 String date1=date.split("-")[0];//年
		 String date2=date.split("-")[1];//月
		 String date9=date.split("-")[2];//日
		 String date10=date9.split(" ")[0];
		 String date3=date1+"-"+date2+"-01";
		 String date4= TimeUtil.addTimeBaseOnNowTime(date3+" 00:00:00", 1);
		Object[] param1 = {
				TimeUtil.StrTsfToDate(date3, "yyyy-MM-dd"),
				TimeUtil.StrTsfToDate(date4,
						"yyyy-MM-dd HH:mm:ss") };

		paramMap.put("createTime", param1);
		typeMap.put("createTime", "bet");
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
		//平台商户月订单数
		int moor=sshUtilBiz.getObjectCount(BaseOrders.class, colName1, queryParam);
		//平台商户月营业额
		double momo=  sshUtilBiz.getSum(BaseOrders.class, colName, queryParam);
		paramMap.clear();
		typeMap.clear();
		paramMap.put("recShopId", json.getLong("shopID"));
		typeMap.put("recShopId", "eq");
			
		paramMap.put("status", 3);
		typeMap.put("status", "eq");
		 String date5=date.split("-")[0];
		
		 String date7=date5+"-01-01";
		 String date8= TimeUtil.addTimeBaseOnNowTime(date7+" 00:00:00", 12);
		Object[] param2 = {
				TimeUtil.StrTsfToDate(date7, "yyyy-MM-dd"),
				TimeUtil.StrTsfToDate(date8,
						"yyyy-MM-dd HH:mm:ss") };

		paramMap.put("createTime", param2);
		typeMap.put("createTime", "bet");
		
		queryParam.setParamMap(paramMap);
		queryParam.setTypeMap(typeMap);
	
		//平台商户年营业额
		double yearmo=  sshUtilBiz.getSum(BaseOrders.class, colName, queryParam);
		
		
		JSONObject msg = new JSONObject();
		msg.element("dayor",dayor );
		msg.element("daymo",daymo);
		msg.element("moor",moor);
		msg.element("momo",momo);
		msg.element("yearmo",yearmo);
		msg.element("year",date1);
		msg.element("month",date2);
		msg.element("day",date10);
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		response.getWriter().println(msg.toString());
	}
	
}
