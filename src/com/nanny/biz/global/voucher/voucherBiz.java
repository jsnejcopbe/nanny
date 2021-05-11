package com.nanny.biz.global.voucher;

import net.sf.json.JSONArray;


/**
 * 抵用券管理业务层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
public interface voucherBiz {
	
	/**
	 * 获得订单arry列表
	 * @param condition
	 * @return
	 */
	public JSONArray doVoucher(JSONArray data);
}
