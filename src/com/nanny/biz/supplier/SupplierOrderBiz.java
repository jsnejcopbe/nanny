package com.nanny.biz.supplier;

import net.sf.json.JSONObject;

import com.zhuoan.ssh.bean.PageUtil;

public interface SupplierOrderBiz {

	
	/**
	 * 供货订单列表（查询）
	 * @param bean
	 * @param pageUtil
	 * @return
	 */


	public JSONObject searchOrder(JSONObject bean,PageUtil pageUtil);
	
	/**
	 * 根据id获取详细
	 * @param id
	 * @return
	 */
	public JSONObject supplierDetById(int supplierId);
	

	/**
	 * 根据信息修改supplier
	 * @param bean
	 * @param id
	 */
	public void updateSupplier(JSONObject bean,int id);
	
	/**
	 * 商家和买家关系
	 * @param bean
	 * @param supplierId
	 * @param shopId
	 */
	public void checkRelation(JSONObject bean);
}
