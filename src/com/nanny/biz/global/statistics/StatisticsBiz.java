package com.nanny.biz.global.statistics;

import net.sf.json.JSONArray;



/**
 * 平台数据统计biz
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */

public interface StatisticsBiz {
	
	/**
	 * 每日订单数
	 *
	 */
	public JSONArray dayordersum(int shopid); 
	
}
