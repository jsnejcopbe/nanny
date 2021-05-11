package com.nanny.biz.user;

import net.sf.json.JSONArray;

/**
 * 买家购物车业务层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserShoppingCarBiz {

	/**
	 * 向购物车添加商品
	 * @param data
	 */
	public void addProduct(JSONArray data,Long userID,Long shopID);
	
	/**
	 * 获取用户在某一家店下的购物车信息
	 * @param userID
	 * @param shopID
	 * @return
	 */
	public JSONArray getCarProListByID(Long userID,Long shopID);
}
