package com.nanny.biz.global.cus;

import net.sf.json.JSONObject;

/**
 * 客户收货地址biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface CusReceiveAddBiz {

	/**
	 * 获得用户的首条收货地址
	 * @param userID
	 * @return
	 */
	public JSONObject getFirstAddByUserID(Long userID);
	
	/**
	 * 根据addID获得地址的详细信息
	 * @param addID
	 * @return
	 */
	public JSONObject getAddDetByAddID(Long addID);
}
