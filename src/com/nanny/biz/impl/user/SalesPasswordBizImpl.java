package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import net.sf.json.JSONObject;

import com.nanny.biz.user.SalesPasswordBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;


@Service("SalesPasswordBiz")
@Transactional
public class SalesPasswordBizImpl implements SalesPasswordBiz{
	
	@Resource
	private SSHUtilDao sshUtilDao;

	@Override
	public String updatePassword(JSONObject bean) {
		
		String pass = bean.getString("password");
		String tel = bean.getString("tel");
		
		String sql="UPDATE base_saleman SET `password`=? WHERE  tel=?";
		Object[] queryParam={pass,tel};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
		return "修改成功";
	}


}
