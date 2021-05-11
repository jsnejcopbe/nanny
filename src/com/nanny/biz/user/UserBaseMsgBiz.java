package com.nanny.biz.user;

import net.sf.json.JSONObject;

/**
 * 用户基础信息biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserBaseMsgBiz {

	/**
	 * 获得用户可用余额
	 * @param userID
	 * @return
	 */
	public Double getUserBalance(Long userID);
	
	/**
	 * 根据用户ID获得用户信息
	 * @param userID
	 * @return
	 */
	public JSONObject getUserMsgByUserID(Long userID);
}
