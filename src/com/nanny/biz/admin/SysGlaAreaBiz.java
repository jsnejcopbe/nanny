package com.nanny.biz.admin;

import com.nanny.model.SysGlobalArea;
import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 总后台营业地区设置业务层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface SysGlaAreaBiz {

	/**
	 * 获得当前系统可用省份
	 * @return
	 */
	public JSONArray getSysProvince();
	
	/**
	 * 获得当前系统可用城市
	 * @param province
	 * @return
	 */
	public JSONArray getSysCity(String province);
	
	/**
	 * 获得当前系统设定的区域信息
	 * @param condition
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getGlaArea(JSONObject condition,PageUtil pageUtil);
	
	/**
	 * 插入新的地图数据
	 * @param bean
	 * @return
	 */
	public Long addNewMap(SysGlobalArea bean);
	
	/**
	 * 更新地址信息
	 * @param bean
	 */
	public void updateMapData(SysGlobalArea bean);
	
	/**
	 * 获得附近地区
	 * @param poiList
	 * @return
	 */
	public JSONArray getSysAreaByPoiData(JSONArray poiList,String city);
	
	/**
	 * 根据城市名和地区名获得相符地址信息
	 * @param city
	 * @param name
	 * @return
	 */
	public JSONArray getSysAreaByCityAndName(String city,String name);
	
	/**
	 * 根据地址名称 获得商户在使用的地址列表
	 * @param name
	 * @return
	 */
	public JSONObject getUsedAddressList(String name,PageUtil pageUtil);
}
