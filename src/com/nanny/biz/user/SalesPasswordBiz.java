package com.nanny.biz.user;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface SalesPasswordBiz {
	/**
	 * 业务员密码修改
	 * @param bean
	 * @return
	 */
	public String updatePassword(JSONObject bean);
	

	
}
