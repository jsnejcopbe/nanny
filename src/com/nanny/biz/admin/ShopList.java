package com.nanny.biz.admin;

import java.util.Map;

/** 商品表
 * @author syl
 *
 */
public interface ShopList {
	/** 初始化列表
	 * @param options
	 * @param shop_id
	 * @return
	 */
	public String commodity_init(Map<String, String> options,String shop_id,String right_shop_id,String isAdmin,String showType);

	/** 获取类别
	 * @param type
	 * @return
	 */
	public String getType(String type);

	/** 获取品牌
	 * @return
	 */
	public String getBrand(String shop_id);
	
	/**
	 * 可否兑换
	 */
	public void upIsexchange(int productid);
}
