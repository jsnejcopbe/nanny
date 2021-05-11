package com.nanny.biz.admin;

import com.nanny.model.BaseSupplier;
import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONObject;

public interface AdminSupplierBiz {
	/**
	 * 供应商详情表
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getBaseSupplierInfo(JSONObject bean,PageUtil pageUtil );
	/**
	 * 添加供应商
	 * @param supplier
	 */
	public void addSupplier(BaseSupplier supplier);
	/**
	 * 开店关店
	 * @param supplierId
	 * @param status
	 */
	public void changeSupplierStatus(int supplierId,int status);
	/**
	 * 查询supplier是否存在
	 * @param bean
	 * @return
	 */
	public boolean checkSupplierExi(JSONObject bean);
	
	/**
	 * 获取订单信息
	 * @param bean
	 * @param pageUtil
	 * @return
	 */
	public JSONObject getOrder(JSONObject bean,PageUtil pageUtil);
	
	public void UpdateSupplierInfo(int id,String memo);
	
	
}
