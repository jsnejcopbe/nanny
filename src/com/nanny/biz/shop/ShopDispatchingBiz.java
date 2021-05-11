package com.nanny.biz.shop;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商户配送地址Biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopDispatchingBiz {

	/**
	 * 根据商户ID获得商户配送范围列表
	 * @param shopID
	 * @return
	 */
	public JSONArray getShopDispatchByShopID(Long shopID);
	
	/**
	 * 更新配送价
	 * @param dptID
	 * @param price
	 */
	public void updateShopDispatchByID(Long dptID,Double price);
	
	/**
	 * 根据地区ID获得店铺配送信息
	 * @param shopID
	 * @param areaID
	 * @return
	 */
	public JSONObject getShopDispatchByAddID(Long shopID,Long areaID);
	
	/**
	 * 根据地区ID获得配送的商户列表
	 * @param addID
	 * @return
	 */
	public JSONArray getShopListByAddID(Long addID);
	
	/**
	 * 根据配送iD更新商户排序
	 * @param dptID
	 * @param num
	 */
	public void updateShopDispatchSortByID(Long dptID,int num);
}
