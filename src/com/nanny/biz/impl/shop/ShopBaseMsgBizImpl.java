package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.shop.ShopBaseMsgBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Transactional
@Service("shopBaseMsgBiz")
public class ShopBaseMsgBizImpl implements ShopBaseMsgBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Double getShopBalanceByShopID(Long shopID) {
		String sql="select balance from base_shop where id=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getDouble("balance");
	}

	public JSONObject getShopBizByOrderID(Long orderID) {
		String sql=" select bo.totalPrice,bo.payUserID,bo.orderCode,bs.id as shopID,bs.shop_name,bs.balance,bs.userID" +
				   " from base_orders bo,base_shop bs" +
				   " where bo.recShopID=bs.id and bo.id=?";
		Object[] param={orderID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0);
	}

}
