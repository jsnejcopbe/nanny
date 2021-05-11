package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.AdminProductBiz;
import com.nanny.biz.admin.AdminReturnPointsBiz;
import com.nanny.model.SysReturnPoints;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class AdminReturnPointsBizImpl implements AdminReturnPointsBiz {

	@Resource
	private SSHUtilDao dao;

	@Override
	public JSONObject sysReturn() {
		String sql="select *from sys_return_points ORDER BY id desc";
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql, null, null));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "createTime");
			
		}
		
		JSONObject bean=new JSONObject();
		bean.element("retp", arr);
		return bean;
	}

	
	
	@Override
	public void addintegralset(SysReturnPoints srp) {
		dao.saveObject(srp);
		
	}



	@Override
	public void updateIntegralSet(int id, double cash, int integral) {
		
		String sql="update sys_return_points set integral=?,cash=? where id=?";
		
		Object[] queryParam={integral,cash,id};
		
		dao.updateObjectBySQL(sql, queryParam);
		
		
	}



	@Override
	public JSONObject setReturnCash() {
		
	
		String sql="select cash from system_setup where id=1";
		
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql, null, null));
		
		return arr.getJSONObject(0);
		
	}



	@Override
	public void updateSysCash(double cash) {
		
		String sql="update system_setup set cash=? where id=1";
		
		Object[] queryParam={cash};
	
		dao.updateObjectBySQL(sql, queryParam);
	
		
	}



	@Override
	public void delSysReturn(SysReturnPoints srp) {
		dao.deleteObject(srp);
		
	}



	@Override
	public void delAll() {
		String sql="DELETE FROM sys_return_points";
		Object[] query={};
		dao.updateObjectBySQL(sql,query);
	}
	
	

}
