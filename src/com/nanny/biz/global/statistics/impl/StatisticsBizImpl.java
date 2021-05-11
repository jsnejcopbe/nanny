package com.nanny.biz.global.statistics.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;

import com.nanny.biz.global.statistics.StatisticsBiz;
import com.zhuoan.ssh.dao.SSHUtilDao;
/**
 * 平台数据统计biz
 * @Copyright Copyright (c) 2016
 * @Company zhouan
 * @author HXQ
 * @version 0.1
 */
@Transactional
@Service
public class StatisticsBizImpl implements StatisticsBiz {

	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONArray dayordersum(int shopid) {
		StringBuffer sql=new StringBuffer();
		sql.append("select DATE_FORMAT(createTime,'%Y-%m-%d') as days,count(id) as count from base_orders");
		sql.append(" WHERE  status=3 ");
		if(shopid!=0){
			sql.append(" and  recShopID="+shopid);
		}
		sql.append(" group by days ");
		
		Object[] queryParam={};
		JSONArray orsum=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), queryParam, null));
		
		return orsum;
	}

}
