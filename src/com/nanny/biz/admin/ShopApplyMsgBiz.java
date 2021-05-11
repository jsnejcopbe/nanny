package com.nanny.biz.admin;

/**
 * 商户申请信息查询biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
public interface ShopApplyMsgBiz {

	/**
	 * 获得未处理商户申请的数量统计
	 * @return
	 */
	public int getWaitDealApplyCount();
}
