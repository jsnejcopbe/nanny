package com.nanny.biz.user;

import net.sf.json.JSONObject;

import com.nanny.model.UserViewShopRec;

/**
 * 微信用户通过扫码进入商户的记录业务层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserViewShopRecBiz {
	
	/**
	 * 添加微信用户通过扫码进入商户的记录
	 * @param bean
	 */
	public void addUserViewShopRec(UserViewShopRec bean);
	
	/**
	 * 新增微信用户登录公众号时的地址信息
	 * @param bean
	 */
	public void addUserViewLocation(UserViewShopRec bean);
	
	/**
	 * 获得指定openID的访问记录
	 * @param openID
	 * @return
	 */
	public JSONObject getViewRecByOpenID(String openID);
	
}
