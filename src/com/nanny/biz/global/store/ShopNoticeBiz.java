package com.nanny.biz.global.store;

import net.sf.json.JSONObject;

/**
 * 店铺公告信息biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopNoticeBiz {

	/**
	 * 获得最新公告
	 * @param shopID
	 * @return
	 */
	public JSONObject getLastNoticeByID(Long shopID);
}
