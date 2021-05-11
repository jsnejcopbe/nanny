package com.nanny.biz.supplier;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 供应商信息管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface SupplierInfoBiz {
	
	/**
	 * 根据supid 获取供应商信息
	 * @return
	 */
	public JSONObject getsupinfo(int supid);
	
	/**
	 * 更新供应商信息
	 * @return
	 */
	public String updinfo(JSONObject obj);
	
	
	
	/**
	 * 添加配送地址
	 * @param gid
	 * @return
	 */
	public String addGlobal(JSONObject obj);
	
	
	/**
	 * 删除配送地址
	 * @param gid
	 * @return
	 */
	public String delGlobal(int gid);
 
	
	
	/** 获取省
	 * @return
	 */
	public String getProvince();

	/** 获取市
	 * @param province
	 * @return
	 */
	public String getCity(String provinceid);
	
	
	/** 获取县区
	 * @param province
	 * @return
	 */
	public String getArea(String cityid);
}
