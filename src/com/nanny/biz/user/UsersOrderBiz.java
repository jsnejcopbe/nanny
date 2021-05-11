package com.nanny.biz.user;

import com.nanny.model.UserPointRedeem;
import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户订单
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
public interface UsersOrderBiz {
	
	
	
	

	/**
	 * 用户订单列表
	 * @param Userid
	 * @param sta
	 * @return
	 */
	public JSONObject doUserorder(int Userid,String sta,String orid,PageUtil pageUtil);
	
	
	
	/**
	 * 支付操作
	 * @param orid 订单list
	 * @param totalPrice 总额
	 * @param uid 用户ID
	 * @return
	 */
	public String updateUserorder(JSONArray orid, long uid, double totalPrice,int point);
	
	/**
	 * 用户收货操作
	 * @param orid
	 * @return
	 */
	public String updateShoporder(long orid,long userid);
	
	
	/**
	 * 订单退款
	 * @param orid
	 * @param memo
	 * @return
	 */
	public String ordeRefund(long userid,long orid,String memo);
	
	
	/**
	 * 货到付款
	 * @param orid
	 */
	public JSONArray cod(JSONArray orid,long userid);
	
	
	
	
	/**
	 * 积分兑换商品
	 * @param orid
	 * @return
	 */
	public JSONObject PointGoods(long orid);



	/**
	 * 获得返还积分
	 * @param TotalPrice
	 * @return
	 */
	public int opoint(double TotalPrice); 
	
	/**
	 * 用户积分兑换商品扣款
	 * @param bean
	 */
	public void updateUserPointForExc(UserPointRedeem bean);
}
