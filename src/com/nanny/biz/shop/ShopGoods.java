package com.nanny.biz.shop;

import java.util.List;

import net.sf.json.JSONArray;

public interface ShopGoods {
	
	/** 一键铺货
	 * @param list 商品表的id集合
	 * @param shop_id shopID
	 * @return
	 */
	public String start_goods(JSONArray list,String shop_id);
	
	
	
	/**
	 * 商品ID 查询供应商
	 * @param proid
	 * @return
	 */
	public JSONArray pro_sup(int proid);
	
	
}
