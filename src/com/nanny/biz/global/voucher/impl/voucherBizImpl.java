package com.nanny.biz.global.voucher.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.voucher.voucherBiz;
import com.nanny.model.SysVoucher;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service
public class voucherBizImpl implements voucherBiz {

	@Resource
	private SSHUtilDao dao;
	
	
	@Override
	public JSONArray doVoucher(JSONArray data) {
		for (int i = 0; i < data.size(); i++) {
			JSONObject bean=data.getJSONObject(i);
			//抵用卷信息
  			 if(bean.getInt("vcID")>0){
  				 SysVoucher sv=(SysVoucher) dao.getObjectById(SysVoucher.class, bean.getLong("vcID"));
  				bean.element("svMoney", sv.getMoney());
  				bean.element("svName", sv.getName());
  				bean.element("svCode", sv.getVouCode());
  			 }
		}
		return data;
	}

}
