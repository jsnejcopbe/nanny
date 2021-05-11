package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import com.nanny.biz.shop.ShopProApplyBiz;
import com.nanny.dto.Dto;
import com.nanny.model.ShopProApply;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service
public class ShopProApplyBizImpl implements ShopProApplyBiz {
	@Resource
	private SSHUtilDao sshUtilDao;
	
	
	@Override
	public String addProApply(ShopProApply shopProApply) {
		sshUtilDao.saveObject(shopProApply);
		return "申请已提交，等待审核";
	}


	@Override
	public JSONArray doProApply(int shopid,String status,PageUtil pageUtil) {
		 StringBuffer sql=new StringBuffer();
		 sql.append("SELECT a.*,b.`name` AS brName,c.`name` AS tyName,d.shop_name FROM shop_pro_apply a ");
		 sql.append(" LEFT JOIN shop_pro_brand b on b.id=a.brandID ");
		 sql.append(" LEFT JOIN shop_pro_type c on c.id=a.typeID ");
		 sql.append(" INNER JOIN base_shop d ON d.id=a.shopID ");
		 sql.append(" WHERE 1=1 ");
		 if(status!=null&&!"-1".equals(status)&&!"".equals(status)){
			 sql.append(" and a.`status`="+status);
		 }
		 if(shopid>0){
			 sql.append(" and a.shopID="+shopid);
		 }
		 sql.append(" ORDER BY a.id DESC ");
		 Object[] queryParam={};
		 JSONArray app=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		 for(int i=0;i<app.size();i++){
				JSONObject bean=app.getJSONObject(i);		
				bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "craeteTime");
				
				if(bean.getInt("status")==Dto.SHOP_PRO_PENDING)
					bean.element("stats", "待处理");
				else if(bean.getInt("status")==Dto.SHOP_PRO_AVAILABLE)
					bean.element("stats", "可使用");
				else if(bean.getInt("status")==Dto.SHOP_PRO_UNAVAILABLE)
					bean.element("stats", "不可用");
				
			}
		return app;
	}


	public int getWaitDelApplyCount() {
		String sql=" select count(id) as count from shop_pro_apply where status=?";
		Object[] param={Dto.SHOP_PRO_PENDING};
		JSONArray data=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		return data.getJSONObject(0).getInt("count");
	}
	
	

}
