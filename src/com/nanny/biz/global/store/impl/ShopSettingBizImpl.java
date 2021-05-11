package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

import com.nanny.biz.global.store.ShopSettingBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopSettingBiz")
public class ShopSettingBizImpl implements ShopSettingBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONArray getShopOfficeHours(Long shopID) {
		String sql=" select * from shop_officehours where shopID=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

}
