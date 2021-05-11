package com.nanny.biz.global.store;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.zhuoan.ssh.bean.PageUtil;

/**
 * 店铺资料biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopMsgBiz {

	/**
	 * 获得商店详细信息
	 * @param shopID
	 * @return
	 */
	public JSONObject getShopMsg(Long shopID);
	
	/**
	 * 获得店铺详细信息
	 * @param shopID
	 * @return
	 */
	public JSONObject getShopDetail(Long shopID);
	
	/**
	 * 获得店铺营业时间
	 * @param shopID
	 * @return
	 */
	public JSONArray getShopOfficeHours(Long shopID);
	
	/**
	 * 根据用户地区 查询符合的商户ID
	 * @param areaID
	 * @return
	 */
	public JSONArray getShopIDListByUserAdd(Long areaID);
	
	/**
	 * 根据用户地区查询符合的商户详情
	 * @param areaID
	 * @return
	 */
	public JSONArray getShopDetListByUserAdd(Long areaID,PageUtil pageUtil);
	
	/**
	 * 获得系统用户列表
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getSysShopList(PageUtil pageUtil,String name);
	
	/**
	 * 取轮波
	 */
	public JSONArray getwheel(Long shopID);
}
