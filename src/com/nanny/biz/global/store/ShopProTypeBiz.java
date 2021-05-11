package com.nanny.biz.global.store;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 店铺商品分类biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface ShopProTypeBiz {

	
	/**
	 * 获得商铺一级分类列表
	 * @param shopID
	 * @return
	 */
	public JSONArray getFirstClassList(Long shopID);
	
	/**
	 * 根据上级ID获得下级类别列表
	 * @param classID
	 * @return
	 */
	public JSONArray getChildClassByID(Long classID);
	
	/**
	 * 获得商品列表树状数据
	 * @param shopID
	 * @return
	 */
	public JSONArray getClassTreeData(Long shopID);
	
	/**
	 * 更新类别排序
	 * @param data
	 */
	public void updateClassSort(JSONArray data);
	
	/**
	 * 根据classID获得类别详细信息
	 * @param classID
	 * @return
	 */
	public JSONObject getClassDetById(Long classID);

	
	/**
	 * 隐藏商品
	 * @param shopID
	 * @return
	 */
	public void hideClassSort(Long id);
	
	/**
	 * 显示商品
	 * @param shopID
	 * @return
	 */
	public void showClassSort(Long id);

	
	/**
	 * 根据parID获得子类
	 * @param parID
	 * @return
	 */
	public JSONArray  getChildClassList(int parID);

}
