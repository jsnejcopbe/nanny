package com.nanny.biz.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zhuoan.ssh.bean.PageUtil;

/**
 * 总后台客户
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

public interface ClienteleBiz {

	/**
	 * 客户列表
	 * @param name
	 * @param pageUtil
	 * @return
	 */
	public JSONObject doClienteleList(String name,String logmin,String logmax, PageUtil pageUtil);
}
