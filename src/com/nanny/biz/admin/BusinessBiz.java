package com.nanny.biz.admin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 总后台商户
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

public interface BusinessBiz {

	/**
	 * 商户列表
	 * @param brandlist
	 * @param name
	 * @param pageUtil
	 * @return
	 */
	public JSONArray doBusinessList(String shopname,String situation, PageUtil pageUtil);
	
	/**
	 * 店铺开关
	 * @param shop
	 * @param user
	 * @return
	 */
	public String updateBusiStatus(BaseShop shop,BaseUsers user);
	
	
	/**
	 * 更改店铺资料
	 * @param name
	 * @param con
	 * @param icon
	 * @param tel
	 * @param shopid
	 * @return
	 */
	public String updateShop(String name,String con,String icon,String tel,int shopid);
	
	/**
	 * 更新店铺返现状态
	 * @param shopID
	 * @param sta
	 */
	public void updateShopSubSta(Long shopID,int sta);
	
	/**
	 * 更新店铺积分兑换状态
	 * @param shopID
	 * @param sta
	 */
	public void updateShopTransfer(Long shopID,int sta);
	
	/**
	 * 获得商户营业额
	 * @param shopID
	 * @return
	 */
	public JSONObject getShopTurnover(Long shopID);
	
	
	public void updateIsVoucher(Long shopId,int isVouchers);
	
}
