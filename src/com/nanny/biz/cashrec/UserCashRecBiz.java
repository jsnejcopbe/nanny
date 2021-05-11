package com.nanny.biz.cashrec;

import net.sf.json.JSONObject;

import com.nanny.model.UserChargeRec;

/**
 * 用户充值记录biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserCashRecBiz {

	/**
	 * 添加新的充值记录
	 * @param bean
	 * @return
	 */
	public Long addUserChargeBiz(UserChargeRec bean);
	
	/**
	 * 获得充值记录
	 * @param orderCode
	 * @return
	 */
	public JSONObject getChargeRec(String orderCode);
	
	/**
	 * 充值成功后更新状态
	 * @param chargeRec
	 * @return
	 */
	public void updateStaAfterCharge(JSONObject chargeRec);
}
