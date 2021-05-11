package com.nanny.biz.global.cus.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.cus.CusReceiveAddBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("cusReceiveAddBiz")
public class CusReceiveAddBizImpl implements CusReceiveAddBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONObject getFirstAddByUserID(Long userID) {
		String sql=" select * from user_receive_add where userID=? limit 1";
		Object[] param={userID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()==0)
			return null;
		else
			return array.getJSONObject(0);
	}
	
	public JSONObject getAddDetByAddID(Long addID){
		String sql=" select * from sys_global_area where id=?";
		Object[] param={addID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()==0)
			return null;
		else
			return array.getJSONObject(0);
	}

}
