package com.nanny.biz.user;

import org.springframework.ui.Model;

public interface Salesman {
	
	/** 业务员主页初始化
	 * @param model
	 * @param id
	 */
	void init(Model model, Long id);

	void store_list_init(Model model);
	
}
