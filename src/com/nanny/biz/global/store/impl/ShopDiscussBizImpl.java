package com.nanny.biz.global.store.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.global.store.ShopDiscussBiz;
import com.nanny.model.ShopDiscuss;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.bean.QueryParam;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("shopDiscussBiz")
public class ShopDiscussBizImpl implements ShopDiscussBiz {

	@Resource
	private SSHUtilDao dao;
	
	public JSONObject getShopDiscuss(PageUtil pageUtil, Long shopID) {
		String sql=" select sd.id,sd.con,sd.img,sd.score,sd.reply,sd.createTime," +
				   " bu.nickName,bu.id as userID,bu.headImg" +
				   " from shop_discuss sd,base_users bu" +
				   " where sd.userID=bu.id and sd.shopID=? order by sd.id desc";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, pageUtil));
		JSONObject disData=new JSONObject();
		disData.element("total", getListCount(shopID));
		disData.element("data", TimeUtil.transTimestamp(array, "createTime", "yyyy-MM-dd HH:mm"));
		disData.element("nowPage", pageUtil.getPageIndex());
		disData.element("size", pageUtil.getPageSize());
		return disData;
	}

	public double getShopScoreAvg(Long shopID){
		String sql=" select AVG(score) as avgscore from shop_discuss where shopID=?";
		Object[] param={shopID.intValue()};
		JSONArray array=JSONArray.fromObject(dao.getObjectListBySQL(sql, param, null));
		String score=array.getJSONObject(0).getString("avgscore");
		if(("null").equals(score))
			return 0;
		else
			return Double.valueOf(score);
	}
	
	/**
	 * 查询评论总条数
	 * @param shopID
	 * @return
	 */
	private int getListCount(Long shopID){
		QueryParam painParam = new QueryParam();// 封装查询参数

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> typeMap = new HashMap<String, String>();
		Map<String, String> orderMap = new HashMap<String, String>();
		paramMap.put("shopId", shopID);
		typeMap.put("shopId", "eq");
		
		painParam.setParamMap(paramMap);
		painParam.setTypeMap(typeMap);
		painParam.setOrderMap(orderMap);
		
		int total = dao.getObjectCount(ShopDiscuss.class, "id",painParam);
		return total;
	}
}
