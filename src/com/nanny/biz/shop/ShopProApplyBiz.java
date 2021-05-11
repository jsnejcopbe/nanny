package com.nanny.biz.shop;

import net.sf.json.JSONArray;


import com.nanny.model.ShopProApply;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 商户添加商品申请
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface ShopProApplyBiz {
	
	/**
	 * 添加商品申请
	 * @param shopProApply
	 * @return
	 */
	public String addProApply(ShopProApply shopProApply);

	
	/**
	 * 新增商品申请列表
	 * @param status
	 * @param pageUtil
	 * @return
	 */
	public JSONArray doProApply(int shopid,String status,PageUtil pageUtil);
	
	/********************************************************************************
	 * 新增 WPH
	 * time 16.02.01
	 * for 待处理的新增商品申请数量计算
	 ********************************************************************************/
	
	/**
	 * 获得待处理的新增商品申请数量
	 * @return
	 */
	public int getWaitDelApplyCount();
}
