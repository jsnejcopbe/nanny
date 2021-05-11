package com.nanny.biz.admin;

import net.sf.json.JSONArray;
import com.nanny.model.SysRedbagRec;
import com.zhuoan.ssh.bean.PageUtil;

/**
 * 总后台提现操作控制层
 * @Copyright Copyright (c) 2015
 * @Company zhouan
 * @author WPH
 * @version 0.1
 */
public interface AdminRedBagRecBiz {
	
	/**
	 * 添加红包发放记录
	 * @param bean
	 * @return
	 */
	public Long addRec(SysRedbagRec bean);
	
	
	
	/********************************************************************************
	 * 新增 HXQ
	 * time 16.02.01
	 * for 总后台红包发放记录
	 ********************************************************************************/
	
	/**
	 * 查询红包记录
	 * @param logmin
	 * @param logmax
	 * @param pageUtil
	 * @return
	 */
	public JSONArray doredbag(String logmin,String logmax,PageUtil pageUtil);
	
	
}
