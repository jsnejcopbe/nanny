package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopDispatchingBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopDispatchingBiz")
public class ShopDispatchingBizImpl implements ShopDispatchingBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONArray getShopDispatchByShopID(Long shopID) {
		String sql=" select sd.id,sd.shopID,sd.fee,sd.adressID,sga.addName" +
				   " from shop_dispatching sd,sys_global_area sga" +
				   " where sd.adressID=sga.id and sd.shopID=?" +
				   " order by sd.id desc";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
	}

	public void updateShopDispatchByID(Long dptID, Double price) {
		String sql=" update shop_dispatching set fee=? where id=?";
		Object[] param={price,dptID.intValue()};
		dao.updateObjectBySQL(sql, param);
	}

	public JSONObject getShopDispatchByAddID(Long shopID, Long areaID) {
		String sql=" select id,shopID,fee from shop_dispatching" +
				   " where adressID=? and shopID=?";
		Object[] param={areaID.intValue(),shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		if(array.size()==0)
			return null;
		else
			return array.getJSONObject(0);
	}

	public JSONArray getShopListByAddID(Long addID) {
		String sql=" select bs.shop_name,sd.shopID,sd.id as dispaID,sga.*" +
				   " from base_shop bs,shop_dispatching sd,sys_global_area sga" +
				   " where bs.id=sd.shopID and sd.adressID=sga.id and sd.adressID=? and bs.situation=?" +
				   " order by sd.memo";
		Object[] param={addID.intValue(),Dto.SHOP_SITU};
		return JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
	}

	public void updateShopDispatchSortByID(Long dptID, int num) {
		String sql=" update shop_dispatching set memo=? where id=?";
		Object[] param={num,dptID.intValue()};
		dao.updateObjectBySQL(sql, param);
	}

}
