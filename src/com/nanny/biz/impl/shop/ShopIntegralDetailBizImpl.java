package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopIntegralDetailBiz;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Service
@Transactional
public class ShopIntegralDetailBizImpl implements ShopIntegralDetailBiz{
	@Resource
	private SSHUtilDao dao;
	
	@Override
	public JSONObject doShopIngeralDetail(JSONObject bean,PageUtil pageUtil) {
		JSONObject shopIntgral=new JSONObject();
		StringBuffer sql=new StringBuffer();
		
		sql.append("select a.*,b.nickName,c.orderCode,c.totalPrice,b.headImg from user_point_redeem a ");
		sql.append("LEFT JOIN base_users b on b.id=a.userID ");
		sql.append("LEFT JOIN base_orders c on c.id=a.orderID ");
		sql.append("where 1=1 ");
	
		if(bean.getInt("flag")==1){
			sql.append(" and b.id="+bean.getInt("userid"));
			
			String sql1="select point from base_users where id="+bean.getInt("userid");
			JSONArray arrPoint=JSONArray.fromObject(dao.getObjectListBySQL(sql1, null, null));
			shopIntgral.element("userPoint", arrPoint.getJSONObject(0).get("point"));
			
			
		}
		
		Object shop_id=bean.get("shop_id");
		
		if(!shop_id.toString().equals("null")){
			sql.append(" and a.shopID="+bean.getInt("shop_id"));
		}
		Object sta=bean.get("status");
		if(sta!=null && !sta.toString().equals("") &&!sta.toString().equals("-1")){
			sql.append(" and a.status="+bean.getInt("status"));
		}
		Object logmin=bean.get("logmin");
		if(logmin!=null && !logmin.toString().equals("")){
			sql.append(" and a.createTime>='"+bean.getString("logmin")+"'");
		}
		Object logmax=bean.get("logmax");
		
		if(logmax!=null && !logmax.toString().equals("")){
			sql.append(" and a.createTime<='"+bean.getString("logmax")+"'");
		}
		
		sql.append(" order by a.id desc");
		
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), null, pageUtil));
		
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "createTime");
	
		}

		int count=Integer.parseInt(dao.getTotal(sql.toString().replace(" a.*,b.nickName,c.orderCode,c.totalPrice,b.headImg ", " count(*) "), null));
		
		

		shopIntgral.element("shopIntgral", arr);
		shopIntgral.element("nowpage", pageUtil.getPageIndex());
		shopIntgral.element("size", pageUtil.getPageSize());
		shopIntgral.element("total", count);
		
		return shopIntgral;

	}

}
