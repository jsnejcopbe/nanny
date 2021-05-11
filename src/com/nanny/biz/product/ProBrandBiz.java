package com.nanny.biz.product;

import com.zhuoan.ssh.bean.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 商品
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

public interface ProBrandBiz {
	
	/**
	 * 品牌管理
	 * @param brandlist
	 * @param name
	 * @param pageUtil
	 * @return
	 */
	public JSONArray BrandList(JSONObject brandlist,String name, PageUtil pageUtil);

	/**
	 * 品牌编辑
	 * @param bean
	 */
	public JSONObject updateProbrand(JSONObject bean);
}
