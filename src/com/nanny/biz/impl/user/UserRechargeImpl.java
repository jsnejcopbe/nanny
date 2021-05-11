package com.nanny.biz.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.user.UserRecharge;
import com.nanny.dto.Dto;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service
@Transactional
public class UserRechargeImpl implements UserRecharge{
	@Resource
	private SSHUtilDao dao;

	@Override
	public String init(String id,Map<String, String> option) {
		String sql = "SELECT * FROM user_charge_rec where userID = ?";
		List<Map<String, String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sql, new Object[]{id}, null);
		for(Map<String, String> map:datalist){
			map.put("createTime", BasisUtil.Time_Format(map));
			Object obj = map.get("status");
			map.put("status", Dto.getCharge_stata(obj.toString()));
		}
		return JsonUtil.getJson(null, datalist);
	}
	
}
