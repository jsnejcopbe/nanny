package com.nanny.biz.shop;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;

public interface ShopIntegralDetailBiz {
	/**
	 * 获取积分明细(商户、总后台、pc)
	 * @param bean
	 * @param pageUtil
	 * @return
	 */
	public JSONObject doShopIngeralDetail(JSONObject bean,PageUtil pageUtil);
}
