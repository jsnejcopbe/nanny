package com.nanny.biz.global.store;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 店铺商品数据biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopProDataBiz {

	/**
	 * 获得指定类别下的商品数据
	 * @param classList
	 * @param firstClassID
	 * @param userID
	 * @return
	 */
	public JSONArray getProListByClass(JSONArray classList,Long firstClassID,Long shopID,PageUtil pageUtil);
	
	/**
	 * 根据条件查询商品数据
	 * @param condition
	 * @return
	 */
	public JSONArray getProDataByCondition(JSONObject condition,PageUtil pageUtil);
	
	
	
	/**
	 * 获得商铺中可兑换商品
	 * @param shopID
	 * @return
	 */
	public JSONArray getProlistIsex(Long shopID,PageUtil pageUtil);
}
