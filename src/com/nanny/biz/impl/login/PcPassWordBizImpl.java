package com.nanny.biz.impl.login;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.login.PcPassWordBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service
public class PcPassWordBizImpl implements PcPassWordBiz{
	@Resource
	private SSHUtilDao Dao;
	
	
	@Override
	public void updatepawork(String tel, String pass) {
		
		String sql="UPDATE base_users SET `password`=? WHERE  tel=?";
		Object[] queryParam={pass,tel};
		Dao.updateObjectBySQL(sql, queryParam);
		
	}

	@Override
	public JSONArray dopawork(String tel) {
		
		String sql="SELECT * FROM base_users WHERE tel=?";
		Object[] queryParam={tel};
		JSONArray pw=JSONArray.fromObject( Dao.getObjectListBySQL(sql, queryParam, null));
		
		return pw;
	}

}
