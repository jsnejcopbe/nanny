package com.nanny.biz.shop;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 商户账目
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author hxq
 * @change wph
 * @version 0.1
 */
public interface ShopAccountBiz {
	
	/**
	 * 查询商户账目
	 * @param status
	 * @return
	 */
	public JSONObject doshopaccount(int toUser,String state,String logmin,String logmax,PageUtil pageUtil);
	
	
	/********************************************************************************
	 * 新增  wph
	 * time 16.01.19
	 * for 商户提现相关业务
	 ********************************************************************************/
	
	/**
	 * 获得用户不可用余额
	 * @param shopID
	 * @return
	 */
	public Double getShopForbidCash(Long shopID);
	
	
	/********************************************************************************
	 * 新增  wph
	 * time 16.02.25
	 * for 商户提现相关业务
	 ********************************************************************************/
	/**
	 * 获得账目余额
	 * @param accID
	 * @return
	 */
	public Double getAccountBalance(Long accID);
	
	/********************************************************************************
	 * 新增  wph
	 * time 16.02.25
	 * for 订单每日返现
	 ********************************************************************************/
	
	public void updateUserBalForRepay(JSONArray data);
}
