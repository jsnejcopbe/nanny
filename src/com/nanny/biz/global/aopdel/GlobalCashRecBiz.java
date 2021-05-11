package com.nanny.biz.global.aopdel;

import net.sf.json.JSONObject;

import com.nanny.model.BaseUsers;
import com.nanny.model.UserAccountsRec;
import com.nanny.model.UserPointRedeem;

/**
 * 全局资金操作记录biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface GlobalCashRecBiz {
	
	/**
	 * 添加操作记录
	 * @param bean
	 * @return
	 */
	public Long addCashRec(UserAccountsRec bean);
	
	
	/**
	 * 添加积分记录
	 * @param upr
	 */
	public void addPointRec(UserPointRedeem upr);
	
	
	
	/**
	 * 返还积分，删除记录
	 * @param obj
	 */
	public void delPointRec(JSONObject obj);
	

	/**
	 * 注册赠送抵用卷
	 * @param obj
	 */
	public void addUserVoucher(BaseUsers bean);
	
}
