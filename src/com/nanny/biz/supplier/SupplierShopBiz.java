package com.nanny.biz.supplier;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;


/**
 * 供应商商户管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface SupplierShopBiz {
	
	/**
	 * 来往商户
	 * @return
	 */
	public JSONObject getsupshop(JSONObject obj,PageUtil pageUtil);
	

}
