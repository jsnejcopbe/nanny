package com.nanny.biz.global.store;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;

/**
 * 店铺评论信息biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopDiscussBiz {

	/**
	 * 获得店铺评论
	 * @param pageUtil
	 * @param shopID
	 * @return
	 */
	public JSONObject getShopDiscuss(PageUtil pageUtil,Long shopID);
	
	/**
	 * 计算店铺评价得分
	 * @param shopID
	 * @return
	 */
	public double getShopScoreAvg(Long shopID);
}
