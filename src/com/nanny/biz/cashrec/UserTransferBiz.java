package com.nanny.biz.cashrec;

import net.sf.json.JSONObject;

import com.nanny.model.UserCashApply;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 用户提现biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserTransferBiz {

	/**
	 * 添加用户提现申请
	 * @param bean
	 * @return
	 */
	public Long addNewTransferApp(UserCashApply bean,Long shopID);
	
	/**
	 * 获得用户提现记录
	 * @param userID
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getTransRec(Long userID,PageUtil pageUtil,JSONObject condition);
	
	/**
	 * 根据ID获得用户提现记录
	 * @param appID
	 * @return
	 */
	public JSONObject getTransRecByID(Long appID);
}
