package com.nanny.biz.global.store;



import net.sf.json.JSONArray;




/**
 * 商品
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author LPC
 * @version 0.1
 */

public interface ProductDetailsBiz {

	/**
	 * 根据商品id获得用商品信息
	 * @param 
	 * @return 
	 */
		public JSONArray getProductDedailsByshopID(String id);
}
