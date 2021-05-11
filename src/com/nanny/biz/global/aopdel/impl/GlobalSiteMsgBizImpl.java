package com.nanny.biz.global.aopdel.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.global.aopdel.GlobalSiteMsgBiz;
import com.nanny.model.UserSiteMessage;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("globalSiteMsgBiz")
public class GlobalSiteMsgBizImpl implements GlobalSiteMsgBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Long addNewSysMsg(UserSiteMessage bean) {
		dao.saveObject(bean);
		return bean.getId();
	}

}
