package com.nanny.biz.product;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商品
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

public interface ProTypeBiz {
	

	/**
	 * 分类更新
	 * @param bean
	 */
	public JSONObject updProType(JSONObject bean);
	
	/**
	 * 获得产品类别列表
	 * @param shopID
	 * @param parID
	 * @return
	 */
	public JSONArray getFirstProCla(Long shopID,Long parID);
}
