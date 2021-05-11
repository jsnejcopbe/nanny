package com.nanny.biz.user;

import com.nanny.model.UserSign;
import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户中心
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
/**
 * @author Administrator
 *
 */
public interface UsersBiz {
	
	/**
	 * 更新个人资料
	 * @param bean
	 * @return
	 */
	public String updateUsers(JSONObject bean);
	
	
	
	/**
	 * 用户签到记录
	 * @param userId
	 * @return
	 */
	public JSONArray dousersign(int userId,PageUtil pageUtil);
	
	
	
	/**
	 * 用户签到
	 * @param userId
	 * @return
	 */
	public void updateusersign(UserSign userSign,long userID);
	
	/**
	 * 收货地址
	 * @param Userid
	 * @return
	 */
	public JSONArray doUseraddress(int Userid);
	
	
	/**
	 * 返回USERID
	 * @param baseUsers
	 * @return
	 */
	public long douser(JSONObject bean);
	
	
	/**
	 * 新增收货地址
	 * @param Userid
	 * @return
	 */
	public String addUseraddress(JSONObject bean);
	
	
	/**
	 * 编辑收货地址
	 * @param Userid
	 * @return
	 */
	public String updateUseraddress(JSONObject bean);
	
	/**
	 * 删除收货地址
	 * @param Userid
	 * @return
	 */
	public String delUseraddress(long receid);
	
	/**
	 * 更新用户的推荐商户
	 * @param userID
	 * @param shopID
	 */
	public void updateUserRecShop(String openID,Long shopID);
	
	/**
	 * 根据用户iD获得用户积分信息
	 * @param userID
	 * @return
	 */
	public int getUserPointByUserID(Long userID);
	
	/**
	 * 用户优惠券
	 * @param bean
	 * @return
	 */
	
	public JSONObject doUserCoupon(int userid,String sta,PageUtil pageUtil);
}
