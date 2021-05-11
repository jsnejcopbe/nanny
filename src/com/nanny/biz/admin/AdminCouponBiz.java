package com.nanny.biz.admin;

import java.text.ParseException;
import java.util.List;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



public interface AdminCouponBiz {

	public JSONObject getCouponList(PageUtil pageUtil,JSONObject bean);
	public int getTotal(JSONObject bean);
}
