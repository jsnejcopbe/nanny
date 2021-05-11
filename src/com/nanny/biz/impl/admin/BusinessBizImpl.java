package com.nanny.biz.impl.admin;
import javax.annotation.Resource;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.admin.BusinessBiz;
import com.nanny.dto.Dto;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.nanny.model.ShopNotice;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.DateUtils;
import com.zhuoan.util.TimeUtil;


@Transactional
@Service
public class BusinessBizImpl implements BusinessBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
		
	@Override
	public JSONArray doBusinessList(String shopname, String situation,
			PageUtil pageUtil) {
		
		StringBuffer sql=new StringBuffer();
		
		sql.append("SELECT a.id,a.shop_icon,a.shop_name,a.isSubsidy,a.isTransfer,b.nickName,b.tel,a.createTime,a.situation,a.isVouchers,c.detAdd,c.addName,a.memo,a.shop_des,d.con FROM base_shop a ");
		sql.append("LEFT JOIN base_users b on b.id=a.userID ");
		sql.append("LEFT JOIN sys_global_area c on c.id=a.adressID ");
		sql.append("LEFT JOIN shop_notice d on d.shopID=a.id ");
		sql.append("WHERE 1=1 ");
		if(!"".equals(shopname) && shopname !=null){ 
			sql.append("and (a.shop_name like '%"+shopname+"%' or b.tel like '%"+shopname+"%' or b.nickName like '%"+shopname+"%') ");
		}
		if(!"".equals(situation) && situation !=null&&!"-1".equals(situation)){
			sql.append("and a.situation="+situation);
		}
		
		sql.append(" ORDER BY  a.id  desc ");
		Object[] queryParam1={};
		JSONArray busin=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam1, pageUtil));
		
		
		for(int i=0;i<busin.size();i++){
			JSONObject bean=busin.getJSONObject(i);
			
			    if("null".equals(bean.getString("con"))){
			    	bean.element("con", "暂无公告");
			    }
			    if("null".equals(bean.getString("detAdd"))){
			    	bean.element("detAdd", "暂无");
			    }
			    if("null".equals(bean.getString("addName"))){
			    	bean.element("addName", "暂无");
			    }
			
			bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");
			if(bean.getInt("situation")==Dto.SHOP_SITU)
				bean.element("status", "开启");
			else 
				bean.element("status", "关闭");
		}
		
		
		return busin;
	}

	@Override
	public String updateBusiStatus(BaseShop shop, BaseUsers user) {
		
		
		sshUtilDao.updateObject(shop);
		sshUtilDao.updateObject(user);
		
		
		return "0";
	}

	@Override
	public String updateShop(String name, String con, String icon, String tel,
			int shopid) {
		
		String sql="UPDATE base_shop SET shop_name=?,shop_icon=?,memo=? WHERE id=?";
		Object[] queryParam={name,icon,tel,shopid};
		sshUtilDao.updateObjectBySQL(sql, queryParam);
		
		String sql1="select count(id) as count from shop_notice WHERE shopID=?";
		Object[] queryParam1={shopid};
		JSONArray  notice =JSONArray.fromObject( sshUtilDao.getObjectListBySQL(sql1, queryParam1, null));
		
		if(notice.getJSONObject(0).getInt("count")>0){
			String sql2="UPDATE shop_notice SET con=? WHERE shopID=?";
			Object[] queryParam2={con,shopid};
			sshUtilDao.updateObjectBySQL(sql2, queryParam2);
		}else{
			ShopNotice notic=new ShopNotice();
			notic.setCon(con);
			notic.setCreateTime(DateUtils.getTimestamp());
			notic.setShopId(Long.valueOf(shopid));
			sshUtilDao.saveObject(notic);
		}
		
		return "success";
	}

	public void updateShopSubSta(Long shopID, int sta) {
		String sql=" update base_shop set isSubsidy=? where id=?";
		Object[] param={sta,shopID.intValue()};
		sshUtilDao.updateObjectBySQL(sql, param);		
	}

	public JSONObject getShopTurnover(Long shopID) {
		String sql=" select SUM(totalPrice) as total,count(id) as count,recShopID as count from base_orders where recShopID=? and status=?";
		Object[] param={shopID.intValue(),Dto.STATU_DONE};
		JSONArray data=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		return data.getJSONObject(0);
	}

	public void updateShopTransfer(Long shopID, int sta) {
		String sql=" update base_shop set isTransfer=? where id=?";
		Object[] param={sta,shopID.intValue()};
		sshUtilDao.updateObjectBySQL(sql, param);	
	}

	@Override
	public void updateIsVoucher(Long shopId,int isVouchers) {
	
			String sql="update base_shop set isVouchers=? where id=?";
			Object[] queryParam={isVouchers,shopId.intValue()};
			sshUtilDao.updateObjectBySQL(sql, queryParam);
	
	}

}
