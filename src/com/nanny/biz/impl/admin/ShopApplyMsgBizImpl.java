package com.nanny.biz.impl.admin;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.admin.ShopApplyMsgBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopApplyMsgBiz")
public class ShopApplyMsgBizImpl implements ShopApplyMsgBiz {

	//载入资源
	@Resource
	private SSHUtilDao dao;
	
	public int getWaitDealApplyCount() {
		String sql=" select count(id) as count from sys_shop_apply where state=?";
		Object[] param={Dto.WAIT_OK};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("count");
	}

}
