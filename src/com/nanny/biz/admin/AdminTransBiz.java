package com.nanny.biz.admin;

import net.sf.json.JSONObject;

/**
 * 总后台提现操作控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
public interface AdminTransBiz {

	/**
	 * 通过提现申请时，更新相关表
	 * @param obj
	 */
	public void updateTablesForPass(JSONObject obj);
	
	/**
	 * 拒绝申请时，更新相关表
	 * @param appID
	 */
	public void updateTablesForRefuse(JSONObject obj);
}
