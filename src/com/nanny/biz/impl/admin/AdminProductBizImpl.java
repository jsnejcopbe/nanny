package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.AdminProductBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("adminProductBiz")
public class AdminProductBizImpl implements AdminProductBiz {

	@Resource
	private SSHUtilDao dao;
	
	public void updateProRecSta(JSONArray data) {
		
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			String sql=" update shop_product set isRecommond=? where id=?";
			
			if(tmpObj.getInt("isRec")==0){
				Object[] param={1,tmpObj.getInt("ID")};
				dao.updateObjectBySQL(sql, param);
			}else{
				Object[] param={0,tmpObj.getInt("ID")};
				dao.updateObjectBySQL(sql, param);
			}
		}
	}

}
