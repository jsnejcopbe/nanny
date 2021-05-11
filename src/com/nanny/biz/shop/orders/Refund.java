package com.nanny.biz.shop.orders;

import java.util.Map;

import net.sf.json.JSONArray;

/**
 * 用户退款申请业务层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
public interface Refund {
	/**
	 * 退款列表
	 * @param option
	 * @param shopID
	 * @return
	 */
	public String init(Map<String, String> option,String shopID);
	
	/**
	 * 根据店铺ID查询待审核的退款申请
	 * @param shopID
	 * @return
	 */
	public int getNewAppCount(Long shopID);
	
	/**
	 * 获取退款信息
	 * @param shopID
	 * @return
	 */
	public JSONArray getRefunMsg(Long shopID);


	/**
	 * 根据订单编号更改退款申请状态
	 * @param orderCode
	 * @return
	 */
	public void updateRefAppStaByOrderCode(String orderCode,int sta);
}
