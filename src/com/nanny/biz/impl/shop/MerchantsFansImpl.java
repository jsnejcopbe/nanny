package com.nanny.biz.impl.shop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.shop.MerchantsFans;
import com.nanny.dto.Dto;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.nanny.util.SqlUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;

@Service
@Transactional
public class MerchantsFansImpl implements MerchantsFans{
	@Resource
	private SSHUtilDao dao;

	@Override
	public String fansinit(String shopID,Map<String, String> option) {
		String sql1 = "SELECT    * FROM   base_users AS bu   LEFT JOIN `base_shop` AS bs   ON bu.`recShopID` = bs.`id`  where 1=1";
		String sql2 = "SELECT    * FROM   base_users AS bu   LEFT JOIN `base_shop` AS bs   ON bu.`recShopID` = bs.`id`   LEFT JOIN  user_receive_add AS ura   ON bu.id = ura.`userID` where 1=1";
		SqlUtil sql = new SqlUtil(sql1);
		
		Map<String, String> where = new LinkedHashMap<String, String>();
		if(option != null){
			String memo = option.get("memo");
			String search = option.get("more_search");
			if(memo != null && !Dto.ERROR_CODE.equals(memo)){
				sql = new SqlUtil(sql2);
				where.put("and ura.memo = ", memo);
			}
			if(search != null && !search.trim().isEmpty()){
				where.put("and (bu.nickName like", search);
				where.put("or bu.tel like", search);
				where.put("or bu.qq like", search+")");
			}
		}
		where.put("AND bs.id =", shopID);
		where.put("and bu.isAdmin =", "0");
		sql.setWhere(where);
		sql.setG_Oby(null, "bu.createTime", "desc");
		sql.start();
		//System.out.println(sql.getFull_sql());
		List<Map<String, String>> datalist = (List<Map<String, String>>) dao.getObjectListBySQL(sql.getFull_sql(), null, null);
		for(Map<String, String> map:datalist){
			map.put("createTime", BasisUtil.Time_Format(map.get("createTime")));
		}
		return JsonUtil.getJson(null, datalist);
	}

	@Override
	public String getArea(String type) {
		String sql = "SELECT  sga.`id` as s_value,sga.`addName` as s_text FROM   base_shop AS ba    LEFT JOIN shop_dispatching AS sd      ON ba.`id` = sd.`shopID`    LEFT JOIN sys_global_area AS sga      ON sd.`adressID` = sga.`id`     WHERE ba.`id` = ?";
		return JsonUtil.getJson(dao.getObjectListBySQL(sql, new Object[]{type}, null));
	}
}
