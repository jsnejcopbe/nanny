package com.nanny.biz.impl.user;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.user.UserBaseMsgBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("userBaseMsgBiz")
public class UserBaseMsgBizImpl implements UserBaseMsgBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Double getUserBalance(Long userID) {
		String sql=" select balance from base_users where id=?";
		Object[] param={userID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getDouble("balance");
	}

	public JSONObject getUserMsgByUserID(Long userID) {
		String sql=" select bu.*,bs.id as shopID" +
				   " from base_users bu" +
				   " left join base_shop bs on bs.userID=bu.id and bs.situation=0" +
				   " where bu.id=?";
		Object[] param={userID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0);
	}

}
