package com.nanny.controller.global;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nanny.biz.shop.orders.ShopOrdersBiz;
import com.zhuoan.util.HttpReqUtil;
import com.zhuoan.util.TimeUtil;
import com.zhuoan.util.weixin.MD5;

/**
 * 全局订单打印控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
@Controller
public class ClobalOrderPrintController {
	
	//载入资源
	@Resource(name="shopOrdersBiz")
	private ShopOrdersBiz g_OrderBiz;

	/**
	 * 订单打印
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("printorderbycloud")
	public void orderPrint(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		//0.获得传入参数
		String orderCode=request.getParameter("orderCode");
		
		//1.获得订单基础信息&订单详情
		JSONObject orderMsg=g_OrderBiz.getOrderBaseByOrderCode(orderCode);//订单基础信息
		JSONArray orderDet=g_OrderBiz.getShopOrdersByOrderID(orderMsg.getLong("id"));
		
		//2.组织打印数据
		Map<String,String> data=createQueryData(orderMsg, orderDet);
		
		String result=HttpReqUtil.doPost("http://open.printcenter.cn:8080/addOrder", data, null, "utf-8");
	
		System.out.println("order print result is:"+result+"\n");
	}
	
	/**
	 * 创建打印数据
	 * @param orderMsg
	 * @param orderDet
	 * @return
	 */
	private Map<String,String> createQueryData(JSONObject orderMsg,JSONArray orderDet){
		Map<String,String> param=new HashMap<String, String>();
		
		param.put("deviceNo", "kdt1023142");
		param.put("key", "f0761");
		param.put("times", "1");
		param.put("printContent", "测试打印");

		return param;
	}
}
