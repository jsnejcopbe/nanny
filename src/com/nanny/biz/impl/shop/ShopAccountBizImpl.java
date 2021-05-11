package com.nanny.biz.impl.shop;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nanny.biz.shop.ShopAccountBiz;
import com.nanny.dto.Dto;
import com.zhuoan.ssh.bean.PageUtil;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TimeUtil;
@Service
@Transactional
public class ShopAccountBizImpl implements ShopAccountBiz {
	@Resource
	public SSHUtilDao sshUtilDao;
	
	@Override
	public JSONObject doshopaccount(int User,String state,String logmin,String logmax,PageUtil pageUtil) {
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

		Object[] queryParam={User,User};
		 //JSONArray rec=JSONArray.fromObject( sshUtilDao.getObjectListBySQL(sql.toString(), queryParam, pageUtil));
		//1.查询数据
		JSONArray rec=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql.toString().replace("$", "a.*,(select nickName from base_users where id = a.otherSide) AS oname,(select nickName from base_users where id = a.userID) AS uname, (select headImg from base_users where id = a.otherSide) AS oimg,(select headImg from base_users where id = a.userID) AS uimg "), queryParam, pageUtil));
			//2.查询总条数
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
				else if(bean.getInt("type")==Dto.CASH_OF_TRANSFERREFUND)
					bean.element("stats", "提现退款");
			}
		 JSONObject obj=new JSONObject();
			obj.element("nowPage", pageUtil.getPageIndex());
			obj.element("rec", rec);
			obj.element("total", count.getJSONObject(0).getInt("total"));
			obj.element("size", pageUtil.getPageSize());
		 return obj;
	}

	
	public Double getShopForbidCash(Long shopID) {
		String sql=" select SUM(totalPrice) as forbidmoney from base_orders where recShopID=? and (`status`=? or `status`=?) and memo is null";
		Object[] param={shopID.intValue(),Dto.STATU_WAITSEND,Dto.STATU_WAITREV};
		JSONArray array=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		if(("null").equals(array.getJSONObject(0).getString("forbidmoney")))
			return (double)0;
		else
			return array.getJSONObject(0).getDouble("forbidmoney");
	}


	public Double getAccountBalance(Long accID) {
		Double outmoney=(double)0;
		Double inmoney=(double)0;
		
		//0.获得支出总金额
		String sql="select SUM(money) as outmoney from user_accounts_rec where userID=?";
		Object[] param={accID.intValue()};
		JSONArray array=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		if(!("null").equals(array.getJSONObject(0).getString("outmoney")))
			outmoney=array.getJSONObject(0).getDouble("outmoney");
		
		//1.获得收入总金额
		sql="select SUM(money) as inmoney from user_accounts_rec where otherSide=?";
		JSONArray array2=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
		if(!("null").equals(array2.getJSONObject(0).getString("inmoney")))
			inmoney=array2.getJSONObject(0).getDouble("inmoney");
		
		return MathDelUtil.halfUp(inmoney - outmoney);
	}


	public void updateUserBalForRepay(JSONArray data) {
		for(int i=0;i<data.size();i++)
		{
			JSONObject tmpObj=data.getJSONObject(i);
			//循环更新用户余额
			String sql=" call upDateShopBalance(?,?,?,@balance)";
			Object[] param={tmpObj.getInt("shopID"),tmpObj.getInt("shopID"),tmpObj.getDouble("backMoney")};
			JSONArray rtnData=JSONArray.fromObject(sshUtilDao.getObjectListBySQL(sql, param, null));
			//存入新的余额
			tmpObj.element("nowBalance", rtnData.getJSONObject(0).getDouble("nowBalance"));
			tmpObj.element("addMoney", tmpObj.getDouble("backMoney"));
		}
	}

}
