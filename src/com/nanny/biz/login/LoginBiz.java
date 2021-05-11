package com.nanny.biz.login;

import javax.servlet.http.HttpSession;


import com.nanny.model.BaseSaleman;
import com.nanny.model.BaseSupplier;

import net.sf.json.JSONObject;


/**
 * 登陆数据层
 * @author lpc
 *
 */
public interface LoginBiz {
	
	/**
	 * 查询登录用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public JSONObject getLoginUserMsg(String tel,String password);
	public JSONObject getLoginUserMsg(String token);
	public JSONObject getLoginUserMsgByOpenId(String openId);
	public JSONObject getLoginUserMsgByShopID(Long shopID);
	
	/**
	 *查询supplier登录信息
	 *@param username
	 *@param password
	 *@return  
	 */
	public JSONObject getLoginSupplierMsg(String tel,String password);
	public boolean getLoginSupplierMsg(String tel);
	public boolean getLoginSupplierName(String supplier_name);
	
	/**
	 * 更新用户openId
	 * @param openId
	 * @param userId
	 */
	public void updateUserOpenId(String openId,Long userId);
	
	/**
	 * 更新用户token
	 * @param token
	 * @param userId
	 */
	public void updateUserToken(String token,Long userId);
	
	
	
	/** 业务员登录
	 * @param bs
	 * @param session 
	 * @return
	 */
	public String getSalesManMsg(BaseSaleman bs,String verifycode,String truecode, HttpSession session);
	
	/**
	 * 查询登录业务员信息
	 * @param username
	 * @param password
	 * @return
	 */
	public JSONObject getLoginSalesMsg(String tel,String password);
	public JSONObject getLoginSalesMsg(String tel);
	
	public JSONObject getLoginSalesMsgByOpenId(String openId);
	
	/**
	 * 更新业务员openId
	 * @param openId
	 * @param userId
	 */
	public void updateSalesOpenId(String openId,Long userId);
	

	/**
	 * 注册supplier用户
	 * @param supplier_name
	 * @param password
	 * @param tel
	 */
	public void addSupplier(BaseSupplier supplier);

}
