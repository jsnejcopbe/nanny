package com.nanny.biz.shop;

import net.sf.json.JSONObject;


/**
 * 商户基础信息查询
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopBaseMsgBiz {

	/**
	 * 根据商户ID查询商户余额
	 * @param shopID
	 * @return
	 */
	public Double getShopBalanceByShopID(Long shopID);
	
	/**
	 * 根据订单ID获得商户信息
	 * @param orderID
	 * @return
	 */
	public JSONObject getShopBizByOrderID(Long orderID);
}
