package com.nanny.biz.shop;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;

public interface ShopProductExchangeBiz {
	/**
	 * 兑换明细
	 * @param bean
	 * @param pageUtil
	 * @return
	 */
	public JSONObject doShopProductExchange(JSONObject bean,PageUtil pageUtil);
	
	/**
	 * 确认兑换
	 * @param id
	 */
	public void upUserPointredeemMemo(int id);
	
	
	/**
	 * 拒绝兑换
	 * @param id
	 */
	public void delUserPointRefuse(int id);
	
	
}
