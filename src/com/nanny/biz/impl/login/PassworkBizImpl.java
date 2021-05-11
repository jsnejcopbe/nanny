package com.nanny.biz.impl.login;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

import com.nanny.biz.login.PassworkBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;
@Transactional
@Service
public class PassworkBizImpl implements PassworkBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
	
	@Override
	public void updatepawork(String tel,String pass) {
		
		
		String sql="UPDATE base_users SET `password`=? WHERE  tel=?";
		Object[] queryParam={pass,tel};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
		
	}

	@Override
	public JSONArray dopawork(String tel) {
		String sql="SELECT * FROM base_users WHERE tel=?";
		Object[] queryParam={tel};
		JSONArray pw=JSONArray.fromObject( sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		
		return pw;
	}

	@Override
	public void updatePhonework(int id, String tel) {
		String sql="UPDATE base_users SET `tel`=? WHERE  id=?";
		Object[] queryParam={tel,id};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
	}

	@Override
	public boolean checkPhone(String tel) {
		boolean b=true;
		String sql="select id from base_users where tel=?";
		Object[] queryParam={tel};
		JSONArray arr=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, queryParam, null));
		if(arr.size()>0){
			b=false;
		}
		return b;
		
	}
	
	
}
