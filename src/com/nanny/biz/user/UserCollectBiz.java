package com.nanny.biz.user;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.model.UserCollection;

/**
 * 用户
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author LPC
 * @version 0.1
 */
public interface UserCollectBiz {

	/**
	 * 新增收藏店铺信息
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 */
	public String addUserCollect(UserCollection bean );
	
	/**
	 * 核对收藏店铺信息（通过userID 和shopID）
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 */
	public JSONObject cheUserCollectByuserID(Long userID,Long shopID);
	/**
	 * 删除收藏店铺信息
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 */
	public String delUserCollectByuserIDandshopID(Long userID,Long shopID);
	/**
	 * 获得收藏店铺信息（通过userID）
	 * @Copyright Copyright (c) 2016
	 * @Company zhouan
	 * @author LPC
	 * @version 0.1
	 */
	 public JSONArray getUserCollectionByuserID(Long userID);
	

}
