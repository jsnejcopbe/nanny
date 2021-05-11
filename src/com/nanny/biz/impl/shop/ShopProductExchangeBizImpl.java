package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopIntegralDetailBiz;
import com.nanny.biz.shop.ShopProductExchangeBiz;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserPointRedeem;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Service
@Transactional
public class ShopProductExchangeBizImpl implements ShopProductExchangeBiz{
	@Resource
	private SSHUtilDao dao;

	@Override
	public JSONObject doShopProductExchange(JSONObject bean, PageUtil pageUtil) {
		JSONObject shopProExc =new JSONObject();
		StringBuffer sql=new StringBuffer();
		sql.append("select a.*,b.name,b.cover,b.price,d.shop_name,d.shop_icon,c.nickName,c.headImg from user_point_redeem a ");
		sql.append("INNER JOIN shop_product b on b.id=a.proID ");
		sql.append("INNER JOIN base_users c on c.id=a.userID ");
		sql.append("INNER JOIN base_shop d on d.id=b.shopID " );
		sql.append("where 1=1 ");
		
		if(bean.getInt("flag")==1){
			sql.append(" and a.userID="+bean.getInt("userId"));
			
			String sql1="select point from base_users where id="+bean.getInt("userId");
			JSONArray arrPoint=JSONArray.fromObject(dao.getObjectListBySQL(sql1, null, null));
			shopProExc.element("userPoint", arrPoint.getJSONObject(0).get("point"));
		}
		Object shop_id=bean.get("shop_id");
		if(!shop_id.toString().equals("null")){
		sql.append(" and a.shopID="+bean.getInt("shop_id"));
		}
		
		Object memo=bean.get("memo");
		if(memo!=null && !memo.toString().equals("") &&!memo.toString().equals("-1")){
			sql.append(" and a.memo="+bean.getInt("memo"));
		}
		
		Object logmin=bean.get("logmin");
		if(logmin!=null && !logmin.toString().equals("")){
			sql.append(" and a.createTime>='"+bean.getString("logmin")+"'");
		}
		Object logmax=bean.get("logmax");
		
		if(logmax!=null && !logmax.toString().equals("")){
			sql.append(" and a.createTime<='"+bean.getString("logmax")+"'");
		}
		
		if(bean.containsKey("queryName") && !("").equals(bean.getString("queryName")))
			sql.append(" and (c.nickName like '%"+bean.getString("queryName")+"%' or c.tel like '%"+bean.getString("queryName")+"%')");
			
		sql.append(" order by a.id desc");
		
		JSONArray arr=JSONArray.fromObject(dao.getObjectListBySQL(sql.toString(), null, pageUtil));
		for(int i=0;i<arr.size();i++){
			JSONObject bean1=arr.getJSONObject(i);
			bean1=TimeUtil.transTimeStamp(bean1, "yyyy-MM-dd HH:mm:ss", "createTime");
	
		}
		
		int count=Integer.parseInt(dao.getTotal(sql.toString().replace(" a.*,b.name,b.cover,b.price,d.shop_name,d.shop_icon,c.nickName,c.headImg "," count(*) " ), null));
		
		
		shopProExc.element("shopProExc", arr);
		shopProExc.element("nowpage", pageUtil.getPageIndex());
		shopProExc.element("size", pageUtil.getPageSize());
		shopProExc.element("total", count);
		
		return shopProExc;
	}

	@Override
	public void upUserPointredeemMemo(int id) {
		String sql="update user_point_redeem set memo=1 where id=?";
		Object[] queryParam={id};
		dao.updateObjectBySQL(sql, queryParam);
		
	}

	@Override
	public void delUserPointRefuse(int id) {
		UserPointRedeem upr=(UserPointRedeem) dao.getObjectById(UserPointRedeem.class, Long.valueOf(id));
		
		BaseUsers bu=(BaseUsers) dao.getObjectById(BaseUsers.class, upr.getUserId());
		bu.setPoint(bu.getPoint()+upr.getPoint());
		
		//退回积分
		dao.updateObject(bu);
		
		//删除记录
		dao.deleteObject(upr);
		
	}
	
	

}
