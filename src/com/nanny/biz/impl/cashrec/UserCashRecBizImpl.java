package com.nanny.biz.impl.cashrec;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.cashrec.UserCashRecBiz;
import com.nanny.dto.Dto;
import com.nanny.model.UserChargeRec;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("userCashRecBiz")
public class UserCashRecBizImpl implements UserCashRecBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Long addUserChargeBiz(UserChargeRec bean) {
		dao.saveObject(bean);
		return bean.getId();
	}

	public JSONObject getChargeRec(String orderCode) {
		String sql=" select * from user_charge_rec where chargeCode=?";
		Object[] param={orderCode};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return TimeUtil.transTimeStamp(array.getJSONObject(0), "yyyy-MM-dd HH:mm:ss", "createTime");
	}

	public void updateStaAfterCharge(JSONObject chargeRec) {
		//0.更新充值记录状态
		String sql=" update user_charge_rec set status=? where id=?";
		Object[] param={Dto.CHARGE_DONE,chargeRec.getInt("id")};
		dao.updateObjectBySQL(sql, param);
		//1.更新用户余额
		sql=" call upDateUserBalance(?,?,?,@balance)";
		Object[] par2={chargeRec.getInt("userID"),chargeRec.getInt("userID"),chargeRec.getDouble("money")};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, par2, null));
		
		chargeRec.element("nowBalance", array.getJSONObject(0).getDouble("nowBalance"));
	}

}
