package com.nanny.biz.shop;

import com.nanny.model.ShopPrintDevice;

import net.sf.json.JSONArray;


/**
 * 打印机Biz
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author tpq
 *
 */
public interface ShopPrintDeviceBiz {
	
	/**
	 * 获取打印机信息
	 * @param shopID
	 * @return
	 */
	public JSONArray getPrintMsg(Long shopID);
	
	/**
	 * 修改打印机信息
	 * @param shopID
	 * @param id
	 */
	public String updatePrintMsg(int type,Long shopID,ShopPrintDevice spd);

	/**
	 * 更新base_shop打印机接口
	 * @param type
	 * @return
	 */
	public String updateShopPrint(int type,Long shopID);
}
