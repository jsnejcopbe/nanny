package com.nanny.biz.admin;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 总后台商户提现申请
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author LPC
 * @version 0.1
 */
public interface CashapplyBiz {
	
	/**
	 * 根据条件查询用户提现
	 * @param condition
	 * @return
	 */
	public JSONObject getAdminCashApplyByCondition(JSONObject condition,PageUtil pageUtil);

   /**
    * 
    */
    public  JSONArray  getAdminCashApply(PageUtil pageUtil); 
    
    public  int  getAdminCashApplycount(PageUtil pageUtil); 
}
