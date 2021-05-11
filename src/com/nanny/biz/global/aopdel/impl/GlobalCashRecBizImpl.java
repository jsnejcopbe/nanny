package com.nanny.biz.global.aopdel.impl;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.nanny.biz.global.aopdel.GlobalCashRecBiz;
import com.nanny.model.BaseShop;
import com.nanny.model.BaseUsers;
import com.nanny.model.UserAccountsRec;
import com.nanny.model.UserPointRedeem;
import com.zhuoan.ssh.dao.SSHUtilDao;
import com.zhuoan.util.MathDelUtil;
import com.zhuoan.util.TimeUtil;

@Transactional
@Service("globalCashRecBiz")
public class GlobalCashRecBizImpl implements GlobalCashRecBiz {

	@Resource
	private SSHUtilDao dao;
	
	public Long addCashRec(UserAccountsRec bean)
	{
		dao.saveObject(bean);
		return bean.getId();
	}

	@Override
	public void addPointRec(UserPointRedeem upr) {
		dao.saveObject(upr);
	}

	@Override
	public void delPointRec(JSONObject obj) {
		//查出积分
		String sql1="SELECT point,memo,id FROM user_point_redeem WHERE userID=? and shopID=? AND  orderID=?";
		Object[] queryParam1={obj.getInt("userID"),obj.getInt("shopID"),obj.getInt("orderID")};
		JSONArray upr=JSONArray.fromObject(dao.getObjectListBySQL(sql1, queryParam1, null));
		
		//计算积分
		int po=0;
		for(int i=0;i<upr.size();i++){
			if(upr.getJSONObject(i).getInt("memo")!=1){
				po+=upr.getJSONObject(i).getInt("point");
				
				//删除记录
				String sql="DELETE FROM user_point_redeem WHERE id=?";
				Object[] queryParam={upr.getJSONObject(i).getInt("id")};
				dao.updateObjectBySQL(sql, queryParam);
			}
		}
		
		//返还积分
		BaseUsers bu=(BaseUsers) dao.getObjectById(BaseUsers.class, obj.getLong("userID"));
		bu.setPoint(bu.getPoint()+po);
		dao.updateObject(bu);
		
		//删除记录
//		String sql="DELETE FROM user_point_redeem WHERE userID=? and shopID=? AND  orderID=?";
//		Object[] queryParam={obj.getInt("userID"),obj.getInt("shopID"),obj.getInt("orderID")};
//		dao.updateObjectBySQL(sql, queryParam);
	}

	@Override
	public void addUserVoucher(BaseUsers bean) {
		//0.查询基础用户信息
		int uid=bean.getId().intValue();
		Long shopid=bean.getRecShopId();
		
		System.out.println("my recShopID is:"+shopid+"\n");
		
		//是否有推荐店家
		if(shopid!=null){
			BaseShop bs=(BaseShop) dao.getObjectById(BaseShop.class, shopid);
			int vs=bs.getIsVouchers();
			
			System.out.println("the shop vs is :"+vs+"\n");
			
			//店家是否是活动商铺
			if(vs!=0){
				//1.判断是否已有优惠券
				String sql=" select count(id) as count from sys_voucher where userID="+uid+" and endTime>='"+TimeUtil.getNowDate()+"'";
				JSONArray count=JSONArray.fromObject(dao.getObjectListBySQL(sql, null, null));
				
				if(count.getJSONObject(0).getInt("count")==0)
				{
					//2.循环插入优惠券
					JSONArray str=new JSONArray(); 
					for (int i = 0; i < 4; i++) {
						String	dyj="DYJ"+TimeUtil.getNowDate("yyyyMMddHHmmssSS")+MathDelUtil.getRandomStr(3);
						str.add(dyj);
					}
					String stat=TimeUtil.getNowDate("yyyy-MM-dd HH:mm:ss");
					String sql1="INSERT INTO sys_voucher(name,userID,vouCode,money,startTime,endTime,maxLimit,status)"+
								"VALUES ('抵用卷',?,?,3,?,'2016-05-31 00:00:00',1,0),('抵用卷',?,?,3,?,'2016-05-31 00:00:00',1,0),('抵用卷',?,?,2,?,'2016-05-31 00:00:00',1,0),('抵用卷',?,?,2,?,'2016-05-31 00:00:00',1,0)";
					Object[] queryParam1={uid,str.get(0).toString(),stat,uid,str.get(1).toString(),stat,uid,str.get(2).toString(),stat,uid,str.get(3).toString(),stat};
					dao.updateObjectBySQL(sql1, queryParam1);
				}
				
				System.out.println("all done!\n");
			}
		}
	}
	
}
