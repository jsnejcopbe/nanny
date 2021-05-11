package com.nanny.biz.admin;

import net.sf.json.JSONArray;

/**
 * 总后台商品管理业务层
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface AdminProductBiz {

	/**
	 * 更新商品推荐状态
	 * @param data
	 * @param sta
	 */
	public void updateProRecSta(JSONArray data);
}
