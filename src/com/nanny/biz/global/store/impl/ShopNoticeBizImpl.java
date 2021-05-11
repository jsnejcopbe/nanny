package com.nanny.biz.global.store.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.store.ShopNoticeBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopNoticeBiz")
public class ShopNoticeBizImpl implements ShopNoticeBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONObject getLastNoticeByID(Long shopID) {
		String sql=" select * from shop_notice where shopID=? order by id desc limit 1";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()>0){
			JSONObject notice= array.getJSONObject(0);
			String con=notice.getString("title")+"："+notice.getString("con");
			notice.element("total",con);
//			if(con.length()>22)
//				notice.element("total", con.substring(0, 21)+"...");
//			else
//				notice.element("total",con);
			return notice;
		}
		else
			return null;
	}

}
