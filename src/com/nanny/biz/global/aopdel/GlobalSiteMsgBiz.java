package com.nanny.biz.global.aopdel;

import com.nanny.model.UserSiteMessage;

/**
 * 全局站内信操作biz
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author wph
 * @version 0.1
 */
public interface GlobalSiteMsgBiz {

	/**
	 * 添加新的站内信
	 * @param bean
	 * @return
	 */
	public Long addNewSysMsg(UserSiteMessage bean);
}
