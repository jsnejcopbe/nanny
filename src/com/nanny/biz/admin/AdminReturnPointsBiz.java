package com.nanny.biz.admin;

import com.nanny.model.SysReturnPoints;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AdminReturnPointsBiz {

	/**
	 * 系统积分兑换
	 * @return
	 */
	public JSONObject sysReturn();
	
	/**
	 * 添加系统积分兑换
	 * @param srp
	 */
	public void addintegralset(SysReturnPoints srp);
	/**
	 * 更改系统积分兑换
	 * @param id
	 * @param cash
	 * @param integral
	 */
	public void updateIntegralSet(int id,double cash,int integral);
	
	/**
	 * 设定积分金额兑换比例
	 * @param cash
	 */
	public JSONObject setReturnCash();
	
	public void updateSysCash(double cash);
	
	public void delSysReturn(SysReturnPoints srp);
	
	public void delAll();
	
}
