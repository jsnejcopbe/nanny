package com.nanny.biz.impl.shop.orders;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nanny.biz.shop.orders.Refund;
import com.nanny.dto.Dto;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.nanny.util.SqlUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service
@Transactional
public class RefundImpl implements Refund{
	@Resource
	private SSHUtilDao dao;

	@SuppressWarnings("unchecked")
	@Override
	public String init(Map<String, String> option,String shopID) {
		SqlUtil sql = new SqlUtil("SELECT    bo.*,   ucr.`status` AS refund_status,   bu.`headImg`,   bu.`nickName`,   bu.`qq` FROM   user_cash_refund AS ucr    JOIN base_orders AS bo      ON ucr.`orderCode` = bo.`orderCode`    JOIN `base_users` AS bu      ON bu.`id` = ucr.`userID`      WHERE 1=1 ");
		Map<String, String> where = new LinkedHashMap<String, String>();
		if(option!=null){
			String more = option.get("more_search");
			if(more != null && !more.trim().isEmpty()){
				where.put("and ( bu.nickName like", more);
				where.put("or bo.orderCode like", more);
				where.put("or bo.recTel like", more+")");
			}
		}
		where.put("and ucr.shopID =", shopID);
		sql.setWhere(where);
		sql.setG_Oby(null, "bo.createTime", null);
		sql.start();
		//System.out.println(sql.getFull_sql());
		List<Map<String, String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sql.getFull_sql(), null, null);
		for(Map<String, String> map:datalist){
			map.put("createTime", BasisUtil.Time_Format(map));
			Object o = map.get("status");
			map.put("status", Dto.getOrderState(o.toString(), shopID));
		}
		return JsonUtil.getJson(null, datalist);
	}

	public int getNewAppCount(Long shopID) {
		String sql=" select count(id) as count from user_cash_refund where shopID=? and status=?";
		Object[] param={shopID.intValue(),0};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array.getJSONObject(0).getInt("count");
	}
	
	@Override
	public JSONArray getRefunMsg(Long shopID){
		String sql = "SELECT    bo.*,   ucr.`status` AS refund_status,   ucr.`memo` AS mo,	bu.`headImg`,   bu.`nickName`,   bu.`qq` FROM   user_cash_refund AS ucr    " +
				"JOIN base_orders AS bo      ON ucr.`orderCode` = bo.`orderCode`    JOIN `base_users` AS bu      ON bu.`id` = ucr.`userID`      WHERE 1=1 AND shopID=? ";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		return array;
		
	}
	
	public void updateRefAppStaByOrderCode(String orderCode, int sta) {
		String sql=" update user_cash_refund set status=? where orderCode=?";
		Object[] param={sta,orderCode};
		dao.updateObjectBySQL(sql, param);
	}
}
