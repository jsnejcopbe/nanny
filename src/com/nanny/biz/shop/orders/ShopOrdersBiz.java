package com.nanny.biz.shop.orders;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 商户订单管理业务层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
public interface ShopOrdersBiz {

	/**
	 * 获得商铺订单ID列表
	 * @param condition
	 * @return
	 */
	public JSONObject getOrdersIdByCon(JSONObject condition,PageUtil pageUtil);
	
	/**
	 * 根据订单ID获得订单基础信息
	 * @param orderId
	 * @return
	 */
	public JSONObject getOrderBaseByOrderID(Long orderId);
	
	/**
	 * 根据订单编号获得订单基础信息
	 * @param code
	 * @return
	 */
	public JSONObject getOrderBaseByOrderCode(String code);
	
	/**
	 * 获得商铺订单详情列表
	 * @param shopID
	 * @return
	 */
	public JSONArray getShopOrdersByOrderID(Long orderID);
	
	/**
	 * 获得商户待处理的订单数量
	 * @param shopID
	 * @return
	 */
	public int getCountOfWaitDetOrders(Long shopID);
	
	/**
	 * 查询指定时间段下的新订单
	 * @param time
	 * @param shopID
	 * @return
	 */
	public int getCountOfWaitDetOrders(String time,Long shopID);
	
	/**
	 * 设置订单状态
	 */
	public void updateOrderSta(Long orderID,int status);
	
	/**
	 * 订单退款
	 * @param param
	 */
	public void updateOrderStaForRef(JSONObject param);
	
	/**
	 * 取消订单（货到付款）
	 * @param param
	 */
	public void updateOrderStaForRefOfRecPay(JSONObject param);
	
	/**
	 * 更新超时订单状态
	 * @param limitTime
	 */
	public void updateOrderStaForOverTime(String limitTime);
	
	public void updateOrderStaForNoPay();
	
	/**
	 * 根据订单编号获得订单信息(微信消息推送专用)
	 * @param orderCode
	 * @return
	 */
	public JSONObject getOrderMsgForWXMail(String orderCode);
	
	/**
	 * 获取本日下单的用户
	 * @return
	 */
	public JSONArray getTodayOrderedShopList(String star,String end);
	
	/**
	 * 根据商铺ID获得本日首次订单数
	 * @param shopID
	 * @param star
	 * @param end
	 * @return
	 */
	public int getTodyFirstOrderCount(Long shopID,String star,String end);
	
	/**
	 * 根据订单ID获得订单取消申请数量
	 * @param orderID
	 * @return
	 */
	public int getRefundCountByOrderID(Long orderID);
}
