package com.nanny.biz.global.store;

import net.sf.json.JSONArray;

/**
 * 店铺信息设置biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopSettingBiz {

	/**
	 * 获得店铺经营时间列表
	 * @param shopID
	 * @return
	 */
	public JSONArray getShopOfficeHours(Long shopID);
}
