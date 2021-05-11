package com.nanny.biz.admin;

import java.util.Map;

import com.nanny.model.BaseSaleman;

/**
 * @author syl
 * 平台推广
 */
public interface ExpandList {
	/** 初始化列表
	 * @param map
	 * @return
	 */
	public String init(Map<String, String> map);

	/** 添加推广员
	 * @param bs
	 * @return
	 */
	public String add(BaseSaleman bs);

	/** 根据id找推广员
	 * @param id
	 * @return
	 */
	public String find(String id);
}
