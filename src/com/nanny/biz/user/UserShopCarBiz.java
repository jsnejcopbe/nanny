package com.nanny.biz.user;

import com.nanny.model.UserPayMsg;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 买家购物车业务层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface UserShopCarBiz {

	/**
	 * 购物车商品
	 * @param data
	 */
	public JSONArray getshopList(int userID,int addressID);
	
	/**
	 * 新增购物车订单
	 * @param bean
	 * @param userID
	 * @return
	 */
	public JSONArray addShopOrder(JSONObject bean,int userID);
	
	/**
	 * 删除购物车记录
	 * @param userID
	 * @return
	 */
	public String delShopOrder(int userID);
	
	
	/**
	 * 将付款订单ID 和userID存入 
	 * @param upm
	 */
	public void savePayMsg(UserPayMsg upm);
	
	/**
	 * 往购物车页面添加积分商品
	 * @return
	 */
	public JSONObject doPointRedeem(JSONObject obj);
	
	
	
	/**
	 * 购物车（今天是否使用过抵用卷）
	 * @param userID
	 * @return
	 */
	public String getshopcarvs(int userID);
	
	
	/**
	 * 获得用户订单支付信息
	 * @param userID
	 * @return
	 */
	public JSONObject getUserPayMsg(Long userID,Double money);
}
