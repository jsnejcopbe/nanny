package com.nanny.biz.shop;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;

/**
 * 商户站内信
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface ShopMesgBiz {

	
	/**
	 * 接受人otherSide(toUser)
	 * 站内信列表
	 * @param toUser
	 * @return
	 */
	public JSONArray getShopMsg(int toUser,String state,PageUtil pageUtil);
}
