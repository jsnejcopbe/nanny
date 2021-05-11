package com.nanny.biz.supplier;

import java.util.List;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * 供应商商品管理
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author hxq
 * @version 0.1
 */
public interface SupplierProductBiz {
	
	
	/**根据供应商ID取供应商品
	 * @param supID
	 * @return
	 */
	public JSONObject dosupro(JSONObject bean,PageUtil pageUtil);
	
	
	

	/**
	 * 供应商铺货列表
	 * @param bean
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getshoplist(JSONObject bean,PageUtil pageUtil);
	
	
	
	
	
	
	/**根据商品ID取供应商品
	 * @param proID
	 * @return
	 */
	public JSONArray dosuedpro(int proid);
	
	

	
	/**
	 * 批量更新库存
	 * @param ids
	 * @param inventorys
	 * @return
	 */
	public String updatebatchin(List<String> ids,List<String> prices);
	
	
	/**
	 * 批量上下架
	 * @param ids
	 * @param proCodes
	 * @param isUse
	 * @return
	 */
	public String updatebatchis(List<String> ids,String isUse);
	
	/**
	 * 批量删除
	 * @param ids
	 * @param proCodes
	 * @return
	 */
	public String delbatchpro(List<String> ids);




	/**
	 * 供应商铺货
	 * @param ids
	 * @param supplierId
	 * @return
	 */
	public String start_supgoods(List<String> ids, int supplierId);
	
	
	
	
}