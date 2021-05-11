package com.nanny.biz.shop;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;


/**
 * 商户选择供应商
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface ShopSupplierBiz {

	/**
	 * 供应商列表
	 * @param bean
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getshopsup(JSONObject bean,PageUtil pageUtil);
	
	
	/**
	 * 商户下单
	 * @param bean
	 * @return
	 */
	public String doshnextro(JSONObject bean);
	
}
