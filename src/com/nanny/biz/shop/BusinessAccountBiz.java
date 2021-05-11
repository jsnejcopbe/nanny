package com.nanny.biz.shop;

import java.util.Map;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 商户资金
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author hxq
 * @change wph
 * @version 0.1
 */
public interface BusinessAccountBiz {
	/**
	 * 查询商户名称
	 * @param id
	 * @return
	 */
	public String getShopName(Long id);
	
	/**
	 * 查询商户id
	 * @param id
	 * @return
	 */
	public String getShopId(Long id);
	
	/**
	 * 查询商户资金
	 * @param status
	 * @return
	 */
	public JSONObject dobussaccount(String userID,String state,String logmin,String logmax,PageUtil pageUtil);
	
	
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

}
