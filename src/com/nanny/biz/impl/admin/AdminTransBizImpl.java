package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.AdminTransBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("adminTransBiz")
public class AdminTransBizImpl implements AdminTransBiz {

	@Resource
	private SSHUtilDao dao;
	
	public void updateTablesForPass(JSONObject obj) {
		//0.更新申请状态
		String sql=" update user_cash_apply set status=? where id=?";
		Object[] param={Dto.TRANSFER_PASS_CHECK,obj.getInt("appID")};
		dao.updateObjectBySQL(sql, param);
		//1.更新用户/商户余额
	}

	public void updateTablesForRefuse(JSONObject obj) {
		//0.更新申请状态
		String sql=" update user_cash_apply set status=? where id=?";
		Object[] param={Dto.TRANSFER_FAIL_CHECK,obj.getInt("appID")};
		dao.updateObjectBySQL(sql, param);
		
		//1.组织用户退款
		if(obj.containsKey("shopID")){
			sql=" call upDateShopBalance(?,?,?,@balance)";
			Object[] par={obj.getInt("shopID"),obj.getInt("shopID"),obj.getDouble("money")};
			JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, par, null));
			obj.element("balance", array.getJSONObject(0).getDouble("nowBalance"));
		}else{
			sql=" call upDateUserBalance(?,?,?,@balance)";
			Object[] par={obj.getInt("userID"),obj.getInt("userID"),obj.getDouble("money")};
			JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, par, null));
			obj.element("balance", array.getJSONObject(0).getDouble("nowBalance"));
		}
	}

}
