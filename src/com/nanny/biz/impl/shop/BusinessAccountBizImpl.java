package com.nanny.biz.impl.shop;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.BusinessAccountBiz;
import com.nanny.dto.Dto;
import com.nanny.util.BasisUtil;
import com.nanny.util.JsonUtil;
import com.nanny.util.SqlUtil;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.TimeUtil;
@Service
@Transactional
public class BusinessAccountBizImpl implements BusinessAccountBiz {
	@Resource
	public SSHUtilDao sshUtilDao;
	
	public String getShopName(Long id){
		String sql = "SELECT * FROM base_shop WHERE id=?";
		Object[] queryParam={id.intValue()};
		JSONArray array = JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql,
				queryParam, null));
		return array.getJSONObject(0).getString("shop_name");
	}
	
	
	public String getShopId(Long id){
		String sql = "SELECT * FROM base_shop WHERE id=?";
		Object[] queryParam={id.intValue()};
		JSONArray array = JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql,
				queryParam, null));
		return array.getJSONObject(0).getString("userID");
	}
	
	@Override
	public JSONObject dobussaccount(String userID,String state,String logmin,String logmax,PageUtil pageUtil) {
			
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  $");
		
		sql.append(" FROM user_accounts_rec a where 1=1 ");
		if(!"".equals(state) && state != null && !"-1".equals(state)){
			sql.append(" and a.type="+state);
		}
		
		if(!"".equals(logmin) && logmin != null){
			sql.append(" and a.createTime>='"+logmin+"'");
		}
		
		if(!"".equals(logmax) && logmax != null){
			sql.append(" and a.createTime<='"+logmax+"'");
		}
		sql.append(" and (a.userID=? or a.otherSide = ?)");
		sql.append(" order by a.id desc");

		Object[] queryParam={userID,userID};
		// 查询数据
		JSONArray rec=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "a.*,(select nickName from base_users where id = a.otherSide) AS oname,(select nickName from base_users where id = a.userID) AS uname, (select headImg from base_users where id = a.otherSide) AS oimg,(select headImg from base_users where id = a.userID) AS uimg  "), queryParam, pageUtil));
		// 查询总条数
		JSONArray count=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "count(a.id) as total"), queryParam, null));
		 for(int i=0;i<rec.size();i++){
				JSONObject bean=rec.getJSONObject(i);		
				bean=TimeUtil.transTimeStamp(bean, "yyyy-MM-dd HH:mm:ss", "createTime");				
				if(bean.getInt("type")==Dto.CASH_OF_REFUND)
					bean.element("stats", "退款");
				else if(bean.getInt("type")==Dto.CASH_OF_CHARGE)
					bean.element("stats", "充值");
				else if(bean.getInt("type")==Dto.CASH_OF_PAYMENT)
					bean.element("stats", "付款");
				else if(bean.getInt("type")==Dto.CASH_OF_WITHDRAW)
					bean.element("stats", "提现");
				else if(bean.getInt("type")==Dto.CASH_OF_BACKPAY)
					bean.element("stats", "返现");
				
			}
		 JSONObject obj=new JSONObject();
			obj.element("nowPage", pageUtil.getPageIndex());
			obj.element("rec", rec);
			obj.element("total", count.getJSONObject(0).getInt("total"));
			obj.element("size", pageUtil.getPageSize());
		 return obj;
	}

	
	public Double getShopForbidCash(Long shopID) {
		String sql=" select SUM(totalPrice) as forbidmoney from base_orders where recShopID=? and (`status`=? or `status`=?)";
		Object[] param={shopID.intValue(),Dto.STATU_WAITSEND,Dto.STATU_WAITREV};
		JSONArray array=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		if(("null").equals(array.getJSONObject(0).getString("forbidmoney")))
			return (double)0;
		else
			return array.getJSONObject(0).getDouble("forbidmoney");
	}

}
