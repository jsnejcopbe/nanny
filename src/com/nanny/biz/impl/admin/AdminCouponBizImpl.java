package com.nanny.biz.impl.admin;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import com.nanny.biz.admin.AdminCouponBiz;

import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class AdminCouponBizImpl implements  AdminCouponBiz{

	@Resource
	private SSHUtilDao dao;

	@Override
	public JSONObject getCouponList(PageUtil pageUtil,JSONObject bean) {
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.*,c.recShopID,c.status,d.* from sys_voucher a ");
		sql.append(" LEFT JOIN base_users b on b.id=a.userID ");
		sql.append(" LEFT JOIN base_orders c on c.vcID=a.id");
		sql.append(" LEFT JOIN base_shop d on d.id=c.recShopID");
		sql.append(" where c.status=3 ");
		
		if(bean.getInt("isShop")==1){
			sql.append(" and c.recShopID="+bean.getInt("shopID"));
		}

		Object status=bean.get("status");
		if(status!=null && !status.toString().equals("") && !status.toString().equals("-1")){
			sql.append(" and a.status="+bean.getInt("status"));
		}
		Object queryName=bean.get("queryName");
		if(queryName!=null && !queryName.toString().trim().equals("")){
			if(bean.getInt("isShop")==1){
				sql.append(" and (b.nickName like '%"+bean.getString("queryName").trim()+"%' or b.tel like '%"+bean.getString("queryName")+"%')");
			}
			else{
				sql.append(" and (b.nickName like '%"+bean.getString("queryName").trim()+"%' or b.tel like '%"+bean.getString("queryName")+"%' or d.shop_name like '%"+bean.getString("queryName")+"%')");
				
			}
		}
		Object logmin=bean.get("logmin");
		if(logmin!=null && !logmin.toString().equals("")){
			sql.append(" and a.usageTime>='"+bean.getString("logmin")+"'");
		}
		Object logmax=bean.get("logmax");
		if(logmax!=null && !logmax.toString().equals("")){
			sql.append(" and a.usageTime<='"+bean.getString("logmax")+"'");
		}
		
		sql.append(" order by a.endtime desc ");
	/*	List<Map<String,Object>> list=(List<Map<String,Object>>)dao.getObjectListBySQL(sql.toString(), null, pageUtil);
		for(Map<String,Object> map:list){

			String time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(map.get("endTime"));
			map.put("endTime", time);
			
		}*/
		
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), null, pageUtil));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "usageTime");
	
		}
		
		int total=Integer.parseInt(dao.getTotal(sql.toString().replace(" a.*,b.*,c.recShopID,c.status,d.* ", " count(*) "), null));
		
		JSONObject jso=new JSONObject();
		jso.element("total", total);
		jso.element("arr", arr);
		jso.element("pageIndex", pageUtil.getPageIndex());
		jso.element("pageSize", pageUtil.getPageSize());
		
		return jso;
	}

	@Override
	public int getTotal(JSONObject bean) {
		StringBuffer sql=new StringBuffer();
		sql.append("select count(*) from sys_voucher a ");
		sql.append(" LEFT JOIN base_users b on b.id=a.userID ");
		sql.append(" where 1=1");
		
		Object status=bean.get("status");
		if(status!=null && !status.toString().equals("") && !status.toString().equals("-1")){
			sql.append(" and a.status="+bean.getInt("status"));
		}
		
		Object queryName=bean.get("queryName");
		if(queryName!=null && queryName.toString().trim().equals("")){
			sql.append(" and (b.nickName='"+bean.getString("queryName")+"' or b.tel='"+bean.getString("queryName")+"')");
		}
		
		int total=Integer.parseInt(dao.getTotal(sql.toString(), null));
		return total;
	}
	


}
