package com.nanny.biz.global.bank;

import com.nanny.model.SysBanks;
import com.nanny.model.UserAccount;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;

import net.sf.json.JSONArray;

/**
 * 银行卡操作
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface BankBiz {
	
	/**
	 * 根据UID 取得银行卡信息 
	 * @param userID
	 * @return
	 */
	public JSONArray bankinfo(int userID);
		
	/**
	 * 银行列表
	 * @return
	 */
	public JSONArray banklist(QueryParam queryParam,PageUtil pageUtil);
	
	/**
	 * 绑定（添加）银行卡
	 
	 * @return
	 */
	public String addbank(UserAccount userAccount);
		
	
	/**
	 * 删除已有银行卡
	 * @param userID
	 */
	public void delbank(int userID);
	/**
	 * 添加银行
	 * @param sysBanks
	 * @return
	 */
	public String addsysBank(SysBanks sysBanks);
	
	/**
	 * 根据UID 更改银行卡信息
	 * @param userID
	 * @param bankID
	 * @param bankcard
	 * @param name
	 * @return
	 */
	public String updatebank(int userID,int bankID,String bankcard,String name);
	
	

}
