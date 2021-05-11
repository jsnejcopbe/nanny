package com.nanny.biz.shop;

import net.sf.json.JSONObject;

import com.nanny.model.ShopSendmsgRec;
import com.zhuoan.ssh.bean.PageUtil;

public interface GroupMessage {
	/**
	 * 根据 shopID查询 粉丝
	 * @param shopID
	 * @param aderssId
	 * @param key
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getSelUserShow(String shopID,Integer aderssId, String key, PageUtil pageUtil);

	/**
	 * 模糊查询 小区name tel qq
	 * 
	 * @param shopID
	 * @param nickName
	 * @param tel
	 * @param qq
	 * @param pageUtil
	 * @return
	 */
	public String getSel(String shopID, String nickName, String tel, String qq,
			PageUtil pageUtil);
	
	
	/**
	 * 添加新的消息发送记录
	 * @param bean
	 * @return
	 */
	public void addNewMsgRec(ShopSendmsgRec bean);
	
	/**
	 * 获得消息记录
	 * @param condition
	 * @return
	 */
	public JSONObject getMsgRec(JSONObject condition,PageUtil pageUtil);
}
